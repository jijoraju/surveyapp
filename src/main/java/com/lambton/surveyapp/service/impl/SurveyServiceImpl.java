/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lambton.surveyapp.db.entities.Survey;
import com.lambton.surveyapp.db.entities.Tag;
import com.lambton.surveyapp.db.entities.User;
import com.lambton.surveyapp.db.query.CommonSearchSpecification;
import com.lambton.surveyapp.db.query.SearchCriteria;
import com.lambton.surveyapp.db.query.SearchOperation;
import com.lambton.surveyapp.db.repository.SurveyRepository;
import com.lambton.surveyapp.db.repository.SurveyResponseRepository;
import com.lambton.surveyapp.db.repository.TagRepository;
import com.lambton.surveyapp.service.SurveyService;
import com.lambton.surveyapp.service.impl.helper.SurveyServiceHelper;
import com.lambton.surveyapp.util.DateUtil;
import com.lambton.surveyapp.view.models.SearchResultVO;
import com.lambton.surveyapp.view.models.SurveyVO;

/**
 * @author Jijo Raju
 * @Since Jun 5, 2021 11:18:09 AM
 *
 */

@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private SurveyRepository surveyRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private SurveyResponseRepository surveyResponseRepository;

	@Override
	public List<SurveyVO> getAll() {
		return null;
	}

	@Override
	public List<SurveyVO> getAllBeforeEndDate() {
		List<Survey> activeSurveys = surveyRepository.findAllBeforeEndDate(new Date());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Survey> unAttendedActiveSurveys = activeSurveys.stream()
				.filter(activeSurvey -> !surveyResponseRepository.existsBySurveyAndUser(activeSurvey, user))
				.collect(Collectors.toList());
		return SurveyServiceHelper.getSurveyVOListFromSurveyList(unAttendedActiveSurveys);
	}

	@Override
	public List<SurveyVO> getAllBeforeStartDate() {
		return SurveyServiceHelper.getSurveyVOListFromSurveyList(surveyRepository.findAllBeforeStartDate(new Date()));
	}

	@Override
	public SurveyVO findOne(String uniqueId) {
		Optional<Survey> survey = surveyRepository.findByUniqueId(uniqueId);
		if (survey.isPresent()) {
			return SurveyServiceHelper.getSurveyVOFromSurvey(survey.get());
		}
		else {
			throw new RuntimeErrorException(new Error("Survey not available"));
		}
	}

	@Override
	public SurveyVO save(SurveyVO surveyVO) {
		Survey survey = SurveyServiceHelper.getSurveyFromSurveyVO(surveyVO);
		survey.setUniqueId(UUID.randomUUID().toString());
		survey.getQuestions().stream().forEach(question -> question.setUniqueId(UUID.randomUUID().toString()));
		survey.setTags(getTagList(surveyVO.getTags()));
		return SurveyServiceHelper.getSurveyVOFromSurvey(surveyRepository.save(survey));
	}

	@Override
	public SurveyVO update(SurveyVO surveyVO) {
		SurveyVO updatedSurveyVO = new SurveyVO();
		if (StringUtils.hasText(surveyVO.getUniqueId())) {

			surveyRepository.findByUniqueId(surveyVO.getUniqueId()).ifPresent(oldSurvey -> {
				Survey surveyToUpdate = SurveyServiceHelper.getSurveyFromSurveyVO(oldSurvey, surveyVO);
				surveyToUpdate.setTags(getTagList(surveyVO.getTags()));
				surveyToUpdate.getQuestions().stream().filter(question -> !StringUtils.hasText(question.getUniqueId()))
						.forEach(newQuestion -> newQuestion.setUniqueId(UUID.randomUUID().toString()));
				SurveyServiceHelper.getSurveyVOFromSurvey(updatedSurveyVO, surveyRepository.save(surveyToUpdate));
			});
		}
		if (StringUtils.hasText(updatedSurveyVO.getUniqueId()))
			return updatedSurveyVO;
		else
			throw new RuntimeErrorException(new Error("Update failed"));
	}

	@Override
	public Void delete(SurveyVO surveyVO) {
		try {
			if (StringUtils.hasText(surveyVO.getUniqueId())) {
				Optional<Survey> surveyToDelete = surveyRepository.findByUniqueId(surveyVO.getUniqueId());
				if (surveyToDelete.isPresent()) {
					surveyRepository.delete(surveyToDelete.get());
				}
				else {
					throw new RuntimeErrorException(new Error("Delete failed"));
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeErrorException(new Error("Delete failed"));
		}
		return null;
	}

	@Override
	public SearchResultVO<SurveyVO> search(Map<String, String> params) {
		try {
			int pageNo;
			if (StringUtils.hasText(params.get("pageNo")))
				pageNo = Integer.parseInt(params.get("pageNo"));
			else
				pageNo = 0;
			Pageable pageable = PageRequest.of(pageNo, 10);
			CommonSearchSpecification<Survey> surveySpec = new CommonSearchSpecification<>();

			populateDateRangeSearchSpecification(surveySpec, params.get("dateRange"));
			if (StringUtils.hasText(params.get("searchKey"))) {
				surveySpec.add(new SearchCriteria("name", params.get("searchKey"), SearchOperation.MATCH));
				surveySpec.add(new SearchCriteria("description", params.get("searchKey"), SearchOperation.MATCH));
			}
			if (StringUtils.hasText(params.get("uniqueId"))) {
				surveySpec.add(new SearchCriteria("uniqueId", params.get("surveyId"), SearchOperation.MATCH));
			}
			Page<Survey> survays = surveyRepository.findAll(surveySpec, pageable);
			return SurveyServiceHelper.getSurveyVOListFromSurveyPages(survays);
		}
		catch (Exception e) {
			throw new RuntimeErrorException(new Error("search failed"));
		}
	}

	/**
	 * @param examSepcification
	 * @param string
	 */
	private void populateDateRangeSearchSpecification(CommonSearchSpecification<Survey> examSepcification,
			String dateRange) {
		if (StringUtils.hasText(dateRange) && dateRange.contains("|")) {
			String[] dates = dateRange.split(Pattern.quote("|"));
			Date[] dateValues = new Date[2];
			dateValues[0] = DateUtil.praseFromString(dates[0], "yyyy-MM-dd");
			dateValues[1] = DateUtil.praseFromString(dates[1], "yyyy-MM-dd");
			examSepcification.add(new SearchCriteria("expiryDate", dateValues, SearchOperation.BETWEEN));
		}
	}

	/**
	 * @param tags
	 * @return
	 */
	private List<String> getTagList(String tags) {
		if (StringUtils.hasLength(tags)) {
			List<String> surveyTags = new LinkedList<>(Arrays.asList(tags.split(" ")));
			List<Tag> existingTags = tagRepository.findAllNameIn(surveyTags);
			existingTags.stream().forEach(tag -> {
				if (surveyTags.contains(tag.getName())) {
					surveyTags.remove(tag.getName());
				}
			});
			List<Tag> newTags = surveyTags.stream().map(name -> {
				Tag tag = new Tag();
				tag.setName(name);
				tag.setUniqueId(UUID.randomUUID().toString());
				existingTags.add(tag);
				return tag;
			}).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(newTags)) {
				tagRepository.saveAll(newTags);
			}
			return existingTags.stream().map(Tag::getName).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

}
