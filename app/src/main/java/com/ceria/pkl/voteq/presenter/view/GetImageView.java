package com.ceria.pkl.voteq.presenter.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.GetAllVoteClient;
import com.ceria.pkl.voteq.models.network.GetImage;
import com.ceria.pkl.voteq.models.pojo.GetAllVoteResponse;
import com.ceria.pkl.voteq.presenter.callback.GetImageCallBack;
import com.ceria.pkl.voteq.presenter.interactor.LoginInteractor;
import com.ceria.pkl.voteq.presenter.interactorImpl.LoginInteractorImpl;
import com.ceria.pkl.voteq.presenter.viewinterface.LoginInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;


/**
 * Created by Haniyah on 12/21/2016.
 */

public class GetImageView implements GetImageCallBack{
    String name;
    Context context;
String TAG = "getImage";

    public GetImageView(Context context, String name) {
        this.context = context;
        this.name = name;
    }

    @Override
    public void getImage(String token, String url) {
        final GetImage downloadService =
                ApiClient.getClient().create(GetImage.class);

        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Call<ResponseBody> call = downloadService.downloadFileWithDynamicUrlAsync("Token token="+token, url);
                Log.d(TAG, url + "  " + token);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "server contacted and has file");

                            boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                            Log.d(TAG, "file download was a success? " + writtenToDisk);
                        } else {
                            Log.d(TAG, "server contact failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }

                });
                return null;
            }

            }.execute();
        }

    @Override
    public void onDestroy() {

    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + "avatar " + name + ".jpg");
            Log.d(TAG, futureStudioIconFile.getName());
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
