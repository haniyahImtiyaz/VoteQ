package com.ceria.pkl.voteq.presenter.view;

import com.ceria.pkl.voteq.models.network.ApiClient;
import com.ceria.pkl.voteq.models.network.GetDetailUserClient;
import com.ceria.pkl.voteq.models.pojo.GetProfileResponse;
import com.ceria.pkl.voteq.models.pojo.User;
import com.ceria.pkl.voteq.presenter.callback.GetProfileCallBack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Haniyah on 12/21/2016.
 */

public class GetProfileView implements GetProfileCallBack {

    String image = null;
    @Override
    public String callGetProfile(String id) {
        final GetDetailUserClient getDetailUserClient = ApiClient.getClient().create(GetDetailUserClient.class);
        Call<GetProfileResponse> call = getDetailUserClient.getDetailUser(id);
        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.code() == 200) {
                    User user = response.body().data;
                    String id = user.id;
                    String name = user.name;
                    String gender = user.gender;
                   // String age = user.age;
                    image = user.image;
                    String city = user.city;
                    String province = user.province;
                  //  String[] list = new String[]{id, name, gender, image, city, province};
                }

            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {

            }
        });
        return image;
    }

    @Override
    public void onDestroy() {

    }
}
