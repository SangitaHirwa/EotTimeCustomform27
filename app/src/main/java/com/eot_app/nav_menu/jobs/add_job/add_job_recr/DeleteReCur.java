package com.eot_app.nav_menu.jobs.add_job.add_job_recr;

/**
 * Created by Mahendra Dabi on 10/4/21.
 */
public class DeleteReCur {
    String jobId;
    String recurStatus;
    String recurType;

    public DeleteReCur(String jobId) {
        this.jobId = jobId;
    }

    public DeleteReCur(String jobId, String recurStatus, String recurType) {
        this.jobId = jobId;
        this.recurStatus = recurStatus;
        this.recurType = recurType;
    }
}
