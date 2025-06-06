package org.example.exam.payload;

import lombok.Data;
import org.example.exam.entity.Question;

import java.util.List;

@Data
public class QuestionRequest {
    private Long examId;
    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public Question toQuestion() {
        Question q = new Question();
        q.setQuestionText(this.questionText);
        q.setOptions(this.options);
        q.setCorrectAnswer(this.correctAnswer);
        return q;
    }
}

