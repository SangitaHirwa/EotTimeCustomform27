package com.eot_app.nav_menu.jobs.job_db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesRspncModel;

import java.util.ArrayList;
import java.util.List;
@Entity
public class OfflieCompleQueAns {
    @PrimaryKey
    @NonNull
    String jobId;
    @TypeConverters(AllQuestionAnswerConverter.class)
    List<QuesRspncModel> allQuestionAnswer = new ArrayList<>();
    @TypeConverters(IsMarkDoneConvrtr.class)
    private List<IsMarkDoneWithJtid> isMarkDoneWithJtId = new ArrayList<>();

    public OfflieCompleQueAns(@NonNull String jobId, List<QuesRspncModel> allQuestionAnswer, List<IsMarkDoneWithJtid> isMarkDoneWithJtId) {
        this.jobId = jobId;
        this.allQuestionAnswer = allQuestionAnswer;
        this.isMarkDoneWithJtId = isMarkDoneWithJtId;
    }

    @NonNull
    public String getJobId() {
        return jobId;
    }

    public void setJobId(@NonNull String jobId) {
        this.jobId = jobId;
    }

    public List<QuesRspncModel> getAllQuestionAnswer() {
        return allQuestionAnswer;
    }

    public void setAllQuestionAnswer(List<QuesRspncModel> allQuestionAnswer) {
        this.allQuestionAnswer = allQuestionAnswer;
    }

    public List<IsMarkDoneWithJtid> getIsMarkDoneWithJtId() {
        return isMarkDoneWithJtId;
    }

    public void setIsMarkDoneWithJtId(List<IsMarkDoneWithJtid> isMarkDoneWithJtId) {
        this.isMarkDoneWithJtId = isMarkDoneWithJtId;
    }
}
