package com.bizwink.webapps.survey.result;

import com.bizwink.webapps.survey.answer.Answer;
import java.util.List;

public abstract interface IResultManager
{
  public abstract List getAllSurveyUsersInfo(int paramInt)
    throws ResultException;

  public abstract Answer getASurveyUser(int paramInt)
    throws ResultException;

  public abstract int getAllSurveyUsersNumBySurveyID(int paramInt)
    throws ResultException;

  public abstract List getAllSurveyUsersInfo(int paramInt1, int paramInt2, int paramInt3)
    throws ResultException;

  public abstract List getAllDefineQuestionsIDBySID(int paramInt)
    throws ResultException;

  public abstract List getAQuestionResultOfUsers(int paramInt1, int paramInt2)
    throws ResultException;

  public abstract List getEachQuestionChatData(int paramInt)
    throws ResultException;

  public abstract List getEachQuestionAnswersChatData(int paramInt)
    throws ResultException;
}