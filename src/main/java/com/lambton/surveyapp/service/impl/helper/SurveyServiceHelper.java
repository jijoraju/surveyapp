/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.util.StringUtils;

import com.lambton.surveyapp.db.entities.Question;
import com.lambton.surveyapp.db.entities.Survey;
import com.lambton.surveyapp.db.entities.Tag;
import com.lambton.surveyapp.db.enums.AnswerType;
import com.lambton.surveyapp.util.DateUtil;
import com.lambton.surveyapp.view.models.QuestionVO;
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
		Survey survey = new Survey();
		survey.setName(surveyVO.getName());
		survey.setDescription(surveyVO.getDescription());
		if (StringUtils.hasLength(surveyVO.getExpiryDate())) {
			survey.setExpiryDate(DateUtil.praseFromString(surveyVO.getExpiryDate(), "yyyy-MM-dd"));
		}
		else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			survey.setExpiryDate(calendar.getTime());
		}
		survey.setQuestions(getQuestionListFromQuestionVOList(surveyVO.getItems()));
		return survey;
	}

	/**
	 * @param questionItems
	 * @return
	 */
	static List<Question> getQuestionListFromQuestionVOList(List<QuestionVO> questionItems) {
		return questionItems.stream().map(SurveyServiceHelper::getQuestionFromQuestionVO).collect(Collectors.toList());
	}

	/**
	 * @param item
	 * @return
	 */
	static Question getQuestionFromQuestionVO(QuestionVO item) {
		Question question = new Question();
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
	 * @param save
	 * @return
	 */
	static SurveyVO getSurveyVOFromSurvey(Survey survey) {
		SurveyVO surveyVO = new SurveyVO();
		surveyVO.setUniqueId(survey.getUniqueId());
		surveyVO.setName(survey.getName());
		surveyVO.setDescription(survey.getDescription());
		surveyVO.setExpiryDate(DateUtil.toFormattedString(survey.getExpiryDate(), "yyyy-MM-dd"));
		surveyVO.setTags(survey.getTags().stream().map(Tag::getName).collect(Collectors.joining(" ")));
		surveyVO.setItems(getQuestionVOListFromQuestionList(survey.getQuestions()));
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

}
