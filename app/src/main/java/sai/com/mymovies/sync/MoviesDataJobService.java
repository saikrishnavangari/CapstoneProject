package sai.com.mymovies.sync;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by krrish on 2/02/2017.
 */

public class MoviesDataJobService extends JobService {
    JobParameters mJobParams;
    SyncMoviesData mSyncMoviesData;

    @Override
    public boolean onStartJob(JobParameters jobParams) {
        mJobParams = jobParams;
        mSyncMoviesData =new SyncMoviesData();
        mSyncMoviesData.getMoviesData(mJobParams.getTag(), this);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(mSyncMoviesData!=null)
            mSyncMoviesData.onServiceCancelled();
        return true;
    }

    public void jobCompleted() {
        this.jobFinished(mJobParams, false);
    }
}
