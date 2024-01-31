package com.eot_app.nav_menu.jobs.job_complation.complat_mvp;

import com.eot_app.nav_menu.jobs.job_db.IsMarkDoneWithJtid;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ans_model.Answer;


import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Sonam-11 on 2020-02-04.
 */
public interface Compl_PI {
    void addEditJobComplation(String jobId, String complNote, ArrayList<Answer> compQueAns, List<String>signAnsPath, List<String>docAnsPath, List<Answer>signQueIdArray, List<Answer>docQueIdArray, List<IsMarkDoneWithJtid> markDoneWithJtids);
    void removeUploadAttchment(String jaId);
}
