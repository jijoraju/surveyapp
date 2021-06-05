/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lambton.surveyapp.db.entities.Survey;
import com.lambton.surveyapp.db.entities.Tag;
import com.lambton.surveyapp.db.repository.SurveyRepository;
import com.lambton.surveyapp.db.repository.TagRepository;
import com.lambton.surveyapp.service.SurveyService;
import com.lambton.surveyapp.service.impl.helper.SurveyServiceHelper;
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

	@Override
	public List<SurveyVO> getAll() {
		return SurveyServiceHelper.getSurveyVOListFromSurveyList(surveyRepository.findAll());
	}
	
	@Override
	public SurveyVO findOne(String uniqueId) {
		Optional<Survey> survey = surveyRepository.findByUniqueId(uniqueId);
		if(survey.isPresent()) {
			return SurveyServiceHelper.getSurveyVOFromSurvey(survey.get());
		}else {
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
		return null;
	}

	@Override
	public Void delete(SurveyVO surveyVO) {
		return null;
	}

	/**
	 * @param tags
	 * @return
	 */
	private List<Tag> getTagList(String tags) {
		if (StringUtils.hasLength(tags)) {
			List<String> surveyTags = new LinkedList<>(Arrays.asList(tags.split(" ")));
			List<Tag> existingTags = tagRepository.findAllNameIn(surveyTags);
			existingTags.stream().forEach(tag -> {
				if (surveyTags.contains(tag.getName())) {
					surveyTags.remove(tag.getName());
				}
			});
			surveyTags.stream().forEach(name -> {
				Tag tag = new Tag();
				tag.setName(name);
				tag.setUniqueId(UUID.randomUUID().toString());
				existingTags.add(tag);
			});
			return existingTags;
		}
		return Collections.emptyList();
	}

	

}
