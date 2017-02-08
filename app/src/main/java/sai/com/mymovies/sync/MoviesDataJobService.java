package sai.com.mymovies.sync;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by krrish on 2/02/2017.
 */

public class MoviesDataJobService extends JobService {
    private static String LOG_TAG=MoviesDataJobService.class.getSimpleName();
    JobParameters mJobParams;
    SyncMoviesData mSyncMoviesData;

    @Override
    public boolean onStartJob(JobParameters jobParams) {
        mJobParams = jobParams;
        mSyncMoviesData =new SyncMoviesData();
        mSyncMoviesData.getMoviesData(mJobParams.getTag(), this);
        Log.d(LOG_TAG+jobParams.getTag(),"implemented");
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
