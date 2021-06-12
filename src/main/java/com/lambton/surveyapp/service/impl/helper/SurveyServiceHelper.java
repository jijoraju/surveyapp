/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lambton.surveyapp.db.entities.Question;
import com.lambton.surveyapp.db.entities.Survey;
import com.lambton.surveyapp.db.entities.Tag;
import com.lambton.surveyapp.db.enums.AnswerType;
import com.lambton.surveyapp.util.DateUtil;
import com.lambton.surveyapp.view.models.QuestionVO;
import com.lambton.surveyapp.view.models.SearchResultVO;
import com.lambton.surveyapp.view.models.SurveyVO;

/**
 * @author Jijo Raju
 * @Since Jun 5, 2021 12:10:04 PM
 *
 */
public interface SurveyServiceHelper {

	/**
	 * @param surveyVO
	 * @return
	 */
	static Survey getSurveyFromSurveyVO(SurveyVO surveyVO) {
		return getSurveyFromSurveyVO(new Survey(), surveyVO);
	}

	/**
	 * @param oldQuestions
	 * @param questionItems
	 * @return
	 */
	static List<Question> updateQuestionListFromQuestionVOList(List<Question> oldQuestions,
			List<QuestionVO> questionItems) {

		List<Question> updatedQuestons = new ArrayList<>();
		questionItems.stream().forEach(questionToUpdate -> oldQuestions.stream()
				.filter(oldQuestion -> StringUtils.hasText(questionToUpdate.getUniqueId())
						&& questionToUpdate.getUniqueId().equals(oldQuestion.getUniqueId()))
				.findFirst().ifPresentOrElse(
						oldQuestion -> updatedQuestons.add(getQuestionFromQuestionVO(oldQuestion, questionToUpdate)),
						() -> updatedQuestons.add(getQuestionFromQuestionVO(questionToUpdate))));
		if (CollectionUtils.isEmpty(updatedQuestons)) {
			throw new RuntimeErrorException(new Error("Invalid Update"));
		}
		return updatedQuestons;
	}

	/**
	 * @param oldQuestion
	 * @param questionToUpdate
	 * @return
	 */
	static Question getQuestionFromQuestionVO(Question question, QuestionVO item) {
		question.setTitle(item.getQuestion());
		question.setDescription(item.getDescription());
		question.setOptionItems(item.getOptionItems());
		AnswerType ansType = AnswerType.getValueFromDescription(item.getAnswerType());
		if (null == ansType) {
			throw new RuntimeErrorException(null, "Invalid Question");
		}
		else {
			question.setAnswerType(ansType);
		}
		return question;
	}

	/**
	 * @param item
	 * @return
	 */
	static Question getQuestionFromQuestionVO(QuestionVO item) {
		return getQuestionFromQuestionVO(new Question(), item);
	}

	/**
	 * @param save
	 * @return
	 */
	static SurveyVO getSurveyVOFromSurvey(Survey survey) {
		SurveyVO surveyVO = new SurveyVO();
		getSurveyVOFromSurvey(surveyVO, survey);
		return surveyVO;
	}

	/**
	 * @param questions
	 * @return
	 */
	static List<QuestionVO> getQuestionVOListFromQuestionList(List<Question> questions) {
		return questions.stream().map(SurveyServiceHelper::getQuestionVOFromQuestion).collect(Collectors.toList());
	}

	/**
	 * @param question
	 * @return
	 */
	static QuestionVO getQuestionVOFromQuestion(Question question) {
		QuestionVO item = new QuestionVO();
		item.setUniqueId(question.getUniqueId());
		item.setQuestion(question.getTitle());
		item.setDescription(question.getDescription());
		item.setAnswerType(question.getAnswerType().getDesc());
		item.setOptionItems(question.getOptionItems());
		return item;
	}

	/**
	 * @param findAll
	 * @return
	 */
	static List<SurveyVO> getSurveyVOListFromSurveyList(List<Survey> surveys) {
		return surveys.stream().map(SurveyServiceHelper::getSurveyVOFromSurvey).collect(Collectors.toList());
	}

	/**
	 * @param oldSurvey
	 * @param surveyVO
	 * @return
	 */
	static Survey getSurveyFromSurveyVO(Survey survey, SurveyVO surveyVO) {
		survey.setName(surveyVO.getName());
		survey.setDescription(surveyVO.getDescription());
		if (StringUtils.hasText(surveyVO.getExpiryDate())) {
			survey.setExpiryDate(DateUtil.praseFromString(surveyVO.getExpiryDate(), "yyyy-MM-dd"));
		}
		else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			survey.setExpiryDate(calendar.getTime());
		}
		survey.setQuestions(updateQuestionListFromQuestionVOList(survey.getQuestions(), surveyVO.getItems()));
		return survey;
	}

	/**
	 * @param updatedSurveyVO
	 * @param updatedSurvey
	 */
	static void getSurveyVOFromSurvey(SurveyVO surveyVO, Survey survey) {
		surveyVO.setUniqueId(survey.getUniqueId());
		surveyVO.setName(survey.getName());
		surveyVO.setDescription(survey.getDescription());
		surveyVO.setExpiryDate(DateUtil.toFormattedString(survey.getExpiryDate(), "yyyy-MM-dd"));
		surveyVO.setTags(survey.getTags().stream().map(Tag::getName).collect(Collectors.joining(" ")));
		surveyVO.setItems(getQuestionVOListFromQuestionList(survey.getQuestions()));
	}

	/**
	 * @param survays
	 * @return
	 */
	static SearchResultVO<SurveyVO> getSurveyVOListFromSurveyPages(Page<Survey> survays) {
		SearchResultVO<SurveyVO> searchResult = new SearchResultVO<>();
		searchResult.getSearchResult().addAll(survays.getContent().stream()
				.map(SurveyServiceHelper::getSurveyVOFromSurvey).collect(Collectors.toList()));
		searchResult.setLastPage(survays.isLast());
		return searchResult;
	}

}
