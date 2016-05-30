package teammates.ui.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teammates.common.datatransfer.FeedbackParticipantType;
import teammates.common.datatransfer.FeedbackQuestionType;
import teammates.common.datatransfer.FeedbackSessionAttributes;
import teammates.common.util.Const;

/**
 * Data model for adding/editing a single question
 *
 */
public class FeedbackQuestionEditForm {
    private String actionLink;
    
    private String courseId;
    private String feedbackSessionName;
    
    private String questionNumberSuffix;
    private String questionText;
    private String questionTypeDisplayName;
    private FeedbackQuestionType questionType;
    private int questionIndex;

    // Used for adding a new question
    private String questionTypeOptions;
    private String doneEditingLink;
    
    private boolean isQuestionHasResponses;
    private List<ElementTag> questionNumberOptions;

    //TODO use element tags or a new class instead of having html in java
    private String questionSpecificEditFormHtml;
    
    private boolean isEditable;
    private FeedbackQuestionFeedbackPathSettings feedbackPathSettings;
    private FeedbackQuestionVisibilitySettings visibilitySettings;

    private String questionId;
    
    public static FeedbackQuestionEditForm getNewQnForm(String doneEditingLink, FeedbackSessionAttributes feedbackSession,
                                                        String questionTypeChoiceOptions, List<ElementTag> giverOptions,
                                                        List<ElementTag> recipientOptions, List<ElementTag> qnNumOptions,
                                                        String newQuestionEditForm) {
        
        FeedbackQuestionEditForm newQnForm = new FeedbackQuestionEditForm();
        
        newQnForm.doneEditingLink = doneEditingLink;
        newQnForm.actionLink = Const.ActionURIs.INSTRUCTOR_FEEDBACK_QUESTION_ADD;
        newQnForm.courseId = feedbackSession.courseId;
        newQnForm.feedbackSessionName = feedbackSession.feedbackSessionName;
        newQnForm.questionNumberSuffix = "";
        
        newQnForm.questionTypeOptions = questionTypeChoiceOptions;
        
        newQnForm.questionNumberOptions = qnNumOptions;
      
        FeedbackQuestionFeedbackPathSettings feedbackPathSettings = new FeedbackQuestionFeedbackPathSettings();
        
        newQnForm.feedbackPathSettings = feedbackPathSettings;
        
        feedbackPathSettings.setGiverParticipantOptions(giverOptions);
        feedbackPathSettings.setRecipientParticipantOptions(recipientOptions);
        feedbackPathSettings.setNumOfEntitiesToGiveFeedbackToValue(1);
        
        newQnForm.questionSpecificEditFormHtml = newQuestionEditForm;
        newQnForm.isEditable = true;
        
        FeedbackQuestionVisibilitySettings visibilitySettings =
                                        getDefaultVisibilityOptions();
        newQnForm.visibilitySettings = visibilitySettings;
        
        return newQnForm;
    }
    
    private static FeedbackQuestionVisibilitySettings getDefaultVisibilityOptions() {
        Map<String, Boolean> isGiverNameVisible = new HashMap<String, Boolean>();
        Map<String, Boolean> isRecipientNameVisible = new HashMap<String, Boolean>();
        Map<String, Boolean> isResponsesVisible = new HashMap<String, Boolean>();
        
        FeedbackParticipantType[] participantTypes = {
                FeedbackParticipantType.INSTRUCTORS,
                FeedbackParticipantType.RECEIVER
        };
        
        for (FeedbackParticipantType participant : participantTypes) {
            isGiverNameVisible.put(participant.name(), true);
            isRecipientNameVisible.put(participant.name(), true);
            isResponsesVisible.put(participant.name(), true);
        }
        
        return new FeedbackQuestionVisibilitySettings(new ArrayList<String>(), isResponsesVisible,
                                                       isGiverNameVisible, isRecipientNameVisible);
    }
    
    public String getCourseId() {
        return courseId;
    }
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public String getFeedbackSessionName() {
        return feedbackSessionName;
    }
    
    public void setFeedbackSessionName(String feedbackSessionName) {
        this.feedbackSessionName = feedbackSessionName;
    }
    
    public boolean isQuestionHasResponses() {
        return isQuestionHasResponses;
    }
    
    public void setQuestionHasResponses(boolean isQuestionHasResponses) {
        this.isQuestionHasResponses = isQuestionHasResponses;
    }
    
    public List<ElementTag> getQuestionNumberOptions() {
        return questionNumberOptions;
    }
    
    public void setQuestionNumberOptions(List<ElementTag> questionNumberOptions) {
        this.questionNumberOptions = questionNumberOptions;
    }
    
    public String getQuestionSpecificEditFormHtml() {
        return questionSpecificEditFormHtml;
    }
    
    public void setQuestionSpecificEditFormHtml(String questionSpecificEditFormHtml) {
        this.questionSpecificEditFormHtml = questionSpecificEditFormHtml;
    }
   
    public String getQuestionText() {
        return questionText;
    }

    public String getAction() {
        return actionLink;
    }
    
    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    /**
     * @see {@link #getQuestionIndexIfNonZero}
     */
    public int getQuestionIndex() {
        return questionIndex;
    }
    
    /**
     * @return empty string if questionIndex is 0 (uninitialised), otherwise the value of the questionIndex
     * @see {@link #getQuestionIndex}. An example of use of this will be if
     *      the html id of elements in the form of a new question is not suffixed by question index
     */
    public String getQuestionIndexIfNonZero() {
        return questionIndex == 0 ? "" : String.valueOf(questionIndex);
    }
    
    public void setAction(String action) {
        this.actionLink = action;
    }

    public String getQuestionTypeOptions() {
        return questionTypeOptions;
    }

    public String getDoneEditingLink() {
        return doneEditingLink;
    }

    public FeedbackQuestionFeedbackPathSettings getFeedbackPathSettings() {
        return feedbackPathSettings;
    }

    public void setFeedbackPathSettings(FeedbackQuestionFeedbackPathSettings generalSettings) {
        this.feedbackPathSettings = generalSettings;
    }

    public String getQuestionNumberSuffix() {
        return questionNumberSuffix;
    }

    public void setQuestionNumberSuffix(String questionNumberSuffix) {
        this.questionNumberSuffix = questionNumberSuffix;
    }

    public void setDoneEditingLink(String doneEditingLink) {
        this.doneEditingLink = doneEditingLink;
    }

    public void setQuestionTypeOptions(String questionTypeOptions) {
        this.questionTypeOptions = questionTypeOptions;
    }

    public FeedbackQuestionVisibilitySettings getVisibilitySettings() {
        return visibilitySettings;
    }

    public void setVisibilitySettings(FeedbackQuestionVisibilitySettings visibilitySettings) {
        this.visibilitySettings = visibilitySettings;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    
    public String getQuestionId() {
        return this.questionId;
    }

    public String getQuestionTypeDisplayName() {
        return questionTypeDisplayName;
    }

    public void setQuestionTypeDisplayName(String questionTypeDisplayName) {
        this.questionTypeDisplayName = questionTypeDisplayName;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public void setQuestionType(FeedbackQuestionType questionType) {
        this.questionType = questionType;
    }
    
    public String getQuestionType() {
        return this.questionType.toString();
    }
    
}
