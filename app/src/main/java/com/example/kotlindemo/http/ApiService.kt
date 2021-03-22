package com.example.kotlindemo.http

import android.graphics.Region
import com.uniqlo.circle.data.source.remote.network.CustomCall
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.intellij.lang.annotations.Language
import org.w3c.dom.Comment
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 *
 * @author at-cuongcao.
 */
interface ApiService {

    companion object {
        private const val OS_TYPE = 1
        private const val LOCAL_SUFFIX = "jp"
    }

//    /**
//     * This method used to call image search by image file.
//     */
//    @Multipart
//    @POST
//    fun imageSearchByFile(
//        @Url url: String,
//        @Part("fq") fqRequestBody: RequestBody?,
//        @Part("result_limit") resultLimitRequestBody: RequestBody?,
//        @Part("page") pageRequestBody: RequestBody?,
//        @Part("sort_type") sortTypeRequestBody: RequestBody,
//        @Part imageFile: MultipartBody.Part?
//    ): CustomCall<ItemSearchResponse>
//
//    /**
//     * This method used to call image search by image id.
//     */
//    @Multipart
//    @POST("v1/$LOCAL_SUFFIX/item/search")
//    fun imageSearchById(
//        @Part("im_id") imageIdRequestBody: RequestBody?,
//        @Part("fq") fqRequestBody: RequestBody?,
//        @Part("result_limit") resultLimitRequestBody: RequestBody?,
//        @Part("page") pageRequestBody: RequestBody?,
//        @Part("sort_type") sortTypeRequestBody: RequestBody
//    ): Single<ItemSearchResponse>
//
//    /**
//     * This method used to login by device id.
//     */
//    @FormUrlEncoded
//    @POST("v1/$LOCAL_SUFFIX/register/device")
//    fun loginDevice(@Field("device_id") deviceId: String): Single<LoginResponse>
//
//    /**
//     * This method used to search outfit by  im_name
//     *
//     * @param imageName - the item which need to be searched.
//     * @param resultLimit - limit count of response record.
//     * @param page - specify the number of page.
//     * @param sortType - sort type for response record
//     *                   "Recent":Return most recent post to the top
//     *                   "Popular":Return post with the most number of stars to the top
//     */
//    @GET("v1/$LOCAL_SUFFIX/outfit/search")
//    fun searchOutfit(
//        @Query("im_name") imageName: String,
//        @Query("result_limit") resultLimit: Int,
//        @Query("page") page: Int,
//        @Query("sort_type") sortType: String
//    ): Single<OutfitResponse>
//
//    /**
//     * This method used to star up outfit item.
//     *
//     * @param idOutfitBody - outfit ID.
//     */
//    @POST("v1/$LOCAL_SUFFIX/outfit/{id}/star")
//    fun starCountUp(@Path("id") id: Int): Single<StarCountResponse>
//
//    /**
//     * This method used to star down outfit item.
//     *
//     * @param idOutfitBody - outfit ID.
//     */
//    @DELETE("v1/$LOCAL_SUFFIX/outfit/{id}/star")
//    fun starCountDown(@Path("id") id: Int): Single<StarCountResponse>
//
//    /**
//     * This method used to detect item from image file.
//     *
//     * @param imageFile - image file to detect.
//     * @param resultLimitRequestBody - result count want to get.
//     */
//    @Multipart
//    @POST
//    fun detectItem(
//        @Url url: String,
//        @Part imageFile: MultipartBody.Part,
//        @Part("result_limit") resultLimitRequestBody: RequestBody
//    ): CustomCall<DetectItemResponse>
//
//    /**
//     * This method used to post outfit to server.
//     */
//    @POST("v1/$LOCAL_SUFFIX/outfit/detail")
//    fun postOutfit(@Body outfit: UpdateOutfitBody): CustomCall<Void>
//
//    /**
//     * This method used to search hashtag by keyword.
//     */
//    @GET("v1/$LOCAL_SUFFIX/outfit/hashtag")
//    fun searchHashtag(
//        @Query("hashtag_key_word", encoded = true) keyWord: String,
//        @Query("result_limit", encoded = true) resultLimit: Int
//    ): Single<HashtagResponse>
//
//    /**
//     * This method used to get outfit explore.
//     */
//    @GET("v1/$LOCAL_SUFFIX/outfit/explore")
//    fun getOutfitExplore(
//        @Query("option_gender") optionGender: Int,
//        @Query("option_recency") optionRecency: Int,
//        @Query("result_limit") resultLimit: Int,
//        @Query("option_location_country") locationCountry: String,
//        @Query("option_location_level_1") locationLevel1: String,
//        @Query("option_location_locality") locationLocality: String,
//        @Query("page") page: Int
//    ): Single<OutfitExploreResponse>
//
//    //get outfit detail.
//    /**
//     * This method used to get detail of outfit by a given outfit id.
//     */
//    @GET("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/detail")
//    fun getOutfitDetail(@Path("id_outfit") id: Int): Single<OutfitDetail>
//
//    /**
//     * This method used to login by Instagram code.
//     */
//    @FormUrlEncoded
//    @POST("v1/$LOCAL_SUFFIX/login/instagram")
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    fun connectInstagram(@Field("code") code: String): Single<LoginSocialNetworkResponse>
//
//    /**
//     * This method used to accept term of use.
//     */
//    @Multipart
//    @POST("v2/$LOCAL_SUFFIX/login/term-accept")
//    fun acceptTermsOfUse(
//        @Part("gender") genderRequestBody: RequestBody,
//        @Part("username") usernameRequestBody: RequestBody,
//        @Part("nickname") nickNameRequestBody: RequestBody,
//        @Part profileImageFile: MultipartBody.Part?,
//        @Part("place_id") placeIdRequestBody: RequestBody?,
//        @Part("place_main_text") placeTextRequestBody: RequestBody?
//    ): Single<AccessToken>
//
//    /**
//     * This method used to text search outfit
//     */
//    @GET("v1/$LOCAL_SUFFIX/item/text-search")
//    fun textSearch(
//        @Query("q", encoded = true) keyWord: String, @Query(
//            "gender",
//            encoded = true
//        ) gender: String, @Query("result_limit", encoded = true) resultLimit: Int, @Query(
//            "page",
//            encoded = true
//        ) page: Int
//    ): CustomCall<TextSearchResponse>
//
//    /**
//     * This method used to get brand list
//     */
//    @GET("v2/$LOCAL_SUFFIX/brands")
//    fun getBrandList(
//        @Query("result_limit", encoded = true) resultLimit: Int, @Query(
//            "brand_key_word",
//            encoded = true
//        ) brandKeyWord: String
//    ): Single<BrandResponse>
//
//    @Multipart
//    @POST("v1/$LOCAL_SUFFIX/outfit/detect-item")
//    fun detectItemLoadMore(
//        @Part("im_id") imIdRequestBody: RequestBody,
//        @Part("page") pageRequestBody: RequestBody,
//        @Part("detection") detectionRequestBody: RequestBody,
//        @Part("box") boxRequestBody: List<Int>,
//        @Part("result_limit") resultLimitRequestBody: RequestBody
//    ): Single<DetectItemResponse>
//
//    /**
//     * This method used to login by Twitter code.
//     */
//    @FormUrlEncoded
//    @POST("v1/$LOCAL_SUFFIX/login/twitter")
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    fun loginTwitter(
//        @Field("oauth_token") oauthToken: String,
//        @Field("oauth_token_secret") oauthTokenSecret: String
//    ):
//            Single<LoginSocialNetworkResponse>
//
//    /**
//     * This method used to request token Twitter
//     */
//    @FormUrlEncoded
//    @POST("v1/$LOCAL_SUFFIX/oauth/request_token")
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    fun requestToken(@Field("oauth_callback") oauthCallback: String):
//            Single<RequestTokenTwitterResponse>
//
//    /**
//     * This method used to access token Twitter
//     */
//    @FormUrlEncoded
//    @POST("v1/$LOCAL_SUFFIX/oauth/access_token")
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    fun getTwitterAccesstoken(@Field("oauth_verifier") oauthVerifier: String):
//            Single<AccessTokenTwitterResponse>
//
//    /**
//     * This method used to check version
//     */
//    @GET("v1/$LOCAL_SUFFIX/version/info")
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    fun getVersionInfo(): Single<ForceUpdateResponse>
//
//    /**
//     * This method used to delete outfit
//     */
//    @DELETE("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/detail")
//    fun deleteOutfit(@Path("id_outfit") outfitId: Int): Single<OutfitDeleteResponse>
//
//    /**
//     * This method used to unDelete outfit
//     */
//    @PUT("v1/$LOCAL_SUFFIX/outfit/detail/{id_outfit}/undelete")
//    fun unDeleteOutfit(@Path("id_outfit") outfitId: Int): Single<OutfitDeleteResponse>
//
//    /**
//     * This method used to update gender profile
//     */
//    @FormUrlEncoded
//    @PUT("v1/$LOCAL_SUFFIX/user/profile/gender")
//    fun updateGenderProfile(@Field("gender") gender: Int): Single<GenderProfileResponse>
//
//    /**
//     * This method used to update detail outfit
//     */
//    @PUT("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/detail")
//    fun updateOutfitDetail(@Path("id_outfit") outfitId: String, @Body outfit: UpdateOutfitDetailBody): CustomCall<Void>
//
//    /**
//     * This method used to get outfit feed
//     */
//    @GET("v1/$LOCAL_SUFFIX/outfit/feed")
//    fun getOutfitFeedList(@Query("result_limit") resultLimit: Int, @Query("max_feed_datetime") maxFeedDatetime: Long?): Single<OutfitFeedResponse>
//
//    /**
//     * This method userd to get item information related the outfit
//     */
//    @GET("v1/$LOCAL_SUFFIX/outfit/item-tag")
//    fun getItemTag(@Query("id_outfit") idOutfit: String): Single<ItemTagResponse>
//
//    /**
//     * This method used to get user information
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/self/profile")
//    fun getUserInformation(): Single<User>
//
//    /**
//     * This method used to get user information by id user app
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/{id_user_app}/profile")
//    fun getUserInformationById(@Path("id_user_app") idUserApp: String): Single<User>
//
//    /**
//     * This method used to get username exists
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/user-name/exists")
//    fun getUserNameExits(@Query("username", encoded = true) userName: String): Single<UsernameExistsResponse>
//
//    /**
//     * This method used to get user's favorite.
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/{id_user_app}/favorite/search")
//    fun getUserFavorite(@Path("id_user_app") idUserApp: String, @Query("page") page: Int, @Query("result_limit") resultLimit: Int): Single<FavoriteOutfitResponse>
//
//    /**
//     * This method used to get Favorite keyword search
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/{id_user_app}/favorite/search")
//    fun getFavoriteSearch(
//        @Path("id_user_app") idUserApp: String, @Query("q", encoded = true) keyWord: String,
//        @Query("result_limit", encoded = true) resultLimit: Int,
//        @Query("page", encoded = true) page: Int
//    ): Single<FavoriteOutfitResponse>
//
//    /**
//     * This method used to get Instagram info
//     */
//    @GET("v2/$LOCAL_SUFFIX/user/instagram/outfit")
//    fun getInstagramOutfit(
//        @Query("count") count: String,
//        @Query("max_id") maxId: String
//    ): Single<InstagramOutfitResponse>
//
//    /**
//     * This method used to get Instagram info
//     */
//    @GET("v2/$LOCAL_SUFFIX/user/instagram/info")
//    fun getInstagramInfo(): CustomCall<InstagramInfo>
//
//    /**
//     * This method used to call instagram access time api.
//     */
//    @PUT("v1/$LOCAL_SUFFIX/user/instagram/access-time")
//    fun accessInstagramTime(): Single<EmptyResponse>
//
//    /**
//     * This method used to get instagram user profile.
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/instagram/profile")
//    fun getInstagramUserProfile(): Single<InstagramUserResponse>
//
//    /**
//     * This method used connect with instagram
//     */
//    @POST("v2/$LOCAL_SUFFIX/user/instagram/connect")
//    @FormUrlEncoded
//    fun connectionInstagram(@Field("code") code: String): Single<InstagramConnectionResponse>
//
//    /**
//     * This method used to verify if email was registered.
//     */
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    @POST("v1/$LOCAL_SUFFIX/auth/email/verification")
//    @FormUrlEncoded
//    fun verifyEmail(@Field("email") email: String, @Field("mode") mode: String, @Field("lang") lang: String): Single<EmailVerificationResponse>
//
//    @Multipart
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    @POST("v1/$LOCAL_SUFFIX/auth/email/accept")
//    fun acceptEmail(
//        @Part profileImageFile: MultipartBody.Part?,
//        @Part("pin") pinRequestBody: RequestBody,
//        @Part("login_id") loginIdRequestBody: RequestBody,
//        @Part("password") passwordRequestBody: RequestBody,
//        @Part("gender") genderRequestBody: RequestBody,
//        @Part("username") usernameRequestBody: RequestBody,
//        @Part("nickname") nickNameRequestBody: RequestBody,
//        @Part("place_id") placeIdRequestBody: RequestBody?,
//        @Part("place_main_text") placeTextRequestBody: RequestBody?,
//        @Part("lang") languageRequestBody: RequestBody?
//    ): Single<AcceptEmailResponse>
//
//    /**
//     * This method used get list location when user search location
//     */
//    @GET("v1/$LOCAL_SUFFIX/location/search")
//    fun getListLocationSearch(
//        @Query("location_key_word", encoded = true) locationKeyWord: String,
//        @Query("result_limit", encoded = true) resultLimit: Int,
//        @Query("accept_language", encoded = true) language: String
//    ): CustomCall<LocationResponse>
//
//    /**
//     * This method used get list location when user search location
//     */
//    @GET("v1/$LOCAL_SUFFIX/location/search")
//    fun getListSuggestLocation(
//        @Query("result_limit", encoded = true) resultLimit: Int,
//        @Query("latitude") latitude: Double,
//        @Query("longitude") longitude: Double,
//        @Query("accept_language", encoded = true) language: String
//    ): Single<LocationResponse>
//
//    /**
//     * This method used update user profile
//     */
//    @Multipart
//    @PUT("v1/$LOCAL_SUFFIX/user/self/profile")
//    fun updateUserProfile(
//        @Part profileImageFile: MultipartBody.Part?,
//        @Part("nickname") nickNameRequestBody: RequestBody?,
//        @Part("gender") genderRequestBody: RequestBody?,
//        @Part("height") heightRequestBody: RequestBody?,
//        @Part("place_id") placeId: RequestBody?,
//        @Part("place_main_text") placeMainText: RequestBody?,
//        @Part("biography") biographyRequestBody: RequestBody?,
//        @Part("profile_site_link") profileSiteLinkRequestBody: RequestBody?,
//        @Part("code") codeRequestBody: RequestBody?
//    ): CustomCall<Void>
//
//    @GET("v1/$LOCAL_SUFFIX/user/{id_user_app}/outfit/upload")
//    fun getOutfitUploadUser(@Path("id_user_app") idUserApp: String, @Query("result_limit") resultLimit: Int, @Query("page") page: Int): Single<OutfitUploadUserResponse>
//
//    @GET("v1/$LOCAL_SUFFIX/user/{id_user_app}/followers")
//    fun getFollowers(
//        @Path("id_user_app") idUserApp: String,
//        @Query("keyword") keyword: String,
//        @Query("result_limit") resultLimit: Int,
//        @Query("page") page: Int
//    ): Single<FollowerResponse>
//
//    @POST("v1/$LOCAL_SUFFIX/user/{id_user_app}/follow")
//    fun follow(@Path("id_user_app") idUserApp: String): Single<FollowResponse>
//
//    @DELETE("v1/$LOCAL_SUFFIX/user/{id_user_app}/follow")
//    fun unFollow(@Path("id_user_app") idUserApp: String): Single<FollowResponse>
//
//    @GET("v1/$LOCAL_SUFFIX/outfit/search/hashtag")
//    fun getHashTagOutfits(@Query("hashtag") hashTag: String, @Query("result_limit") resultLimit: Int, @Query("page") page: Int): Single<HashTagOutfitResponse>
//
//    @GET("v1/$LOCAL_SUFFIX/user/{id_user_app}/followings")
//    fun getFollowings(
//        @Path("id_user_app") idUserApp: String,
//        @Query("keyword") keyword: String,
//        @Query("result_limit") resultLimit: Int,
//        @Query("page") page: Int
//    ): Single<FollowingResponse>
//
//    @GET("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/comment")
//    fun getComments(
//        @Path("id_outfit") idOutfit: Int, @Query("result_limit") resultLimit: Int,
//        @Query("since_id") sinceId: Int, @Query("reply_to") replyTo: Int
//    ): Single<CommentResponse>
//
//    @POST("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/comment")
//    fun postComment(@Path("id_outfit") idOutfit: Int, @Body commentBody: PostCommentBody): Single<Comment>
//
//    @PATCH("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/comment/{id_outfit_comment}/delete")
//    fun deleteComment(@Path("id_outfit") idOutfit: Int, @Path("id_outfit_comment") idOutfitComment: Int): CustomCall<Void>
//
//    @PATCH("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/comment/{id_outfit_comment}/undelete")
//    fun unDeleteComment(@Path("id_outfit") idOutfit: Int, @Path("id_outfit_comment") idOutfitComment: Int): CustomCall<Void>
//
//    @GET("v1/$LOCAL_SUFFIX/user/username/{username}/id")
//    fun getUserIdFromUsername(@Path("username") username: String): Single<UserIdResponse>
//
//    @GET("v1/$LOCAL_SUFFIX/user/mention")
//    fun getMentions(@Query("q") q: String, @Query("result_limit") resultLimit: Int): Single<MentionResponse>
//
//    /**
//     * This method used reset password of user login by email normal
//     */
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    @POST("v1/$LOCAL_SUFFIX/user/fr/password/reset")
//    @FormUrlEncoded
//    fun resetPassWord(@Field("email") email: String, @Field("lang") lang: String): Single<ResetPassWordResponse>
//
//    /**
//     * This method used change password
//     */
//    @POST("v1/$LOCAL_SUFFIX/user/fr/password/update")
//    @FormUrlEncoded
//    fun changePassword(
//        @Field("password") password: String, @Field("new_password") newPassword: String, @Field("lang") lang: String, @Field(
//            "code"
//        ) code: String
//    )
//            : Single<ChangePasswordResponse>
//
//    /**
//     * This method used to update email for FR user
//     */
//    @FormUrlEncoded
//    @POST("v1/$LOCAL_SUFFIX/user/fr/email/update")
//    fun updateEmailForFRUser(
//        @Field("password") password: String,
//        @Field("pin") pin: String,
//        @Field("lang") lang: String,
//        @Field("code") code: String
//    ): Single<UpdateEmailFRUserResponse>
//
//    /**
//     * This method used to get user information
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/fr/email")
//    fun getFrEmail(): Single<FrEmailResponse>
//
//    /**
//     * This method used to update email for FR user
//     */
//    @FormUrlEncoded
//    @POST("v1/$LOCAL_SUFFIX/user/fr/logout")
//    fun logOutFRUser(@Field("uuid") uuid: String): Single<LogoutResponse>
//
//    /**
//     * This method used reset password of user login by email normal
//     */
//    @Headers("client-id: ${BuildConfig.APP_CLIENT_ID}")
//    @POST("v1/$LOCAL_SUFFIX/user/fr/password/reset/confirm")
//    @FormUrlEncoded
//    fun createNewPass(
//        @Field("reset_token") resetToken: String,
//        @Field("password") password: String,
//        @Field("password_confirm") passwordConfirm: String,
//        @Field("lang") lang: String
//    ): Single<CreateNewPassWordResponse>
//
//    /**
//     * Get Token FR
//     */
//    @GET("v1/$LOCAL_SUFFIX/token/refresh")
//    fun checkFrToken(): Single<AccessToken>
//
//    /**
//     * Get Reason Delete Account
//     */
//    @GET("v1/$LOCAL_SUFFIX/selection")
//    fun getSelection(@Query("code_type") codeType: Int): Single<SelectionResponse>
//
//    /**
//     * Delete Account Confirm
//     */
//    @PATCH("v1/$LOCAL_SUFFIX/user/delete")
//    @FormUrlEncoded
//    fun deleteAccountConfirm(
//        @Field("reason") reason: Int,
//        @Field("code") code: String?,
//        @Field("access_token") accessToken: String?,
//        @Field("oauth_token") oauthToken: String?,
//        @Field("oauth_token_secret") oauthTokenSecret: String?,
//        @Field("lang") lang: String,
//        @Field("region_code") regionCode: String?
//    ): Single<DeleteAccountConfirmResponse>
//
//    /**
//     * Get suggestion
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/suggestion")
//    fun getSuggestion(
//        @Query("result_limit") resultLimit: Int,
//        @Query("page") page: Int
//    ): Single<SuggestionResponse>
//
//    /**
//     * Get user explore
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/explore")
//    fun getUserExplores(
//        @Query("option_gender") optionGender: Int,
//        @Query("option_recency") optionRecency: Int,
//        @Query("result_limit") resultLimit: Int,
//        @Query("option_location_country") locationCountry: String,
//        @Query("option_location_level_1") locationLevel1: String,
//        @Query("option_location_locality") locationLocality: String,
//        @Query("page") page: Int
//    ): Single<UserExploreResponse>
//
//    /**
//     * This method used to search outfit with texts
//     */
//    @GET("v1/$LOCAL_SUFFIX/outfit/text-search")
//    fun searchTextOutfit(
//        @Query("q", encoded = true) keyWord: String, @Query(
//            "result_limit",
//            encoded = true
//        ) resultLimit: Int, @Query("page", encoded = true) page: Int
//    ): Single<SearchOutfitResponse>
//
//    /**
//     * This method used to search item with texts
//     */
//    @GET("v1/$LOCAL_SUFFIX/item/text-search")
//    fun searchTextItem(
//        @Query("q", encoded = true) keyWord: String, @Query(
//            "save_history",
//            encoded = true
//        ) isSaveHistory: Boolean = true,
//        @Query("result_limit", encoded = true) resultLimit: Int,
//        @Query("page", encoded = true) page: Int
//    ): Single<SearchItemResponse>
//
//    /**
//     * This method used to get search history
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/search/history")
//    fun getSearchHistories(@Query("tab_category") tabCategory: Int?): Single<SearchHistoryResponse>
//
//    /**
//     * This method used to post star count up item
//     */
//    @POST("v1/$LOCAL_SUFFIX/item/{id_pf_item}/star")
//    fun itemStarCountUp(@Path("id_pf_item") idPrItem: Int): Single<StarCountResponse>
//
//    /**
//     * This method used to get badge response
//     */
//    @GET("v1/$LOCAL_SUFFIX/notification/badge")
//    fun getNotificationBadge(): Single<NotificationBadgeResponse>
//
//    /**
//     * This method used to get announcement information
//     */
//    @GET("v1/$LOCAL_SUFFIX/announcement/info")
//    fun getAnnouncementInformation(): Single<AnnouncementInfoResponse>
//
//    /**
//     * This method used to get user upload visibility
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/upload/visibility")
//    fun getUserUploadVisibility(): Single<UploadVisibilityResponse>
//
//    /**
//     * This method used to put user upload visibility
//     */
//    @PUT("v1/$LOCAL_SUFFIX/user/upload/visibility")
//    fun putUserUploadVisibility(): CustomCall<Void>
//
//    /**
//     * This method used to get list notification
//     */
//    @GET("v1/$LOCAL_SUFFIX/notification/info")
//    fun getNotifications(
//        @Query("result_limit") resultLimit: Int,
//        @Query("since_id") sinceId: Int
//    ): CustomCall<NotificationInfoResponse>
//
//    @POST("v1/$LOCAL_SUFFIX/user/push/token")
//    @FormUrlEncoded
//    fun pushDeviceToken(
//        @Field("token") token: String,
//        @Field("uuid") uuid: String,
//        @Field("os_type") osType: Int = OS_TYPE
//    ): Single<EmptyResponse>
//
//    /**
//     * This method used to get push notification setting
//     */
//    @GET("v1/$LOCAL_SUFFIX/notification/setting")
//    fun getPushNotificationSetting(): Single<PushNotificationSettingResponse>
//
//    /**
//     * This method used to push notification setting
//     */
//    @FormUrlEncoded
//    @PUT("v1/$LOCAL_SUFFIX/notification/setting")
//    fun putPushNotificationSetting(
//        @Field("follow") follow: Boolean,
//        @Field("star") star: Boolean,
//        @Field("comment") comment: Boolean,
//        @Field("mention") mention: Boolean,
//        @Field("news") news: Boolean,
//        @Field("ranking") ranking: Boolean,
//        @Field("user_level") userLevel: Boolean
//    ): CustomCall<Void>
//
//    /**
//     * This method used to get a similar outfit list
//     */
//    @GET("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/similar")
//    fun getSimilarOutfits(
//        @Path("id_outfit") outfitId: Int,
//        @Query("result_limit") resultLimit: Int,
//        @Query("page") page: Int
//    ): Single<SimilarOutfitResponse>
//
//    /**
//     * This method used to logout sns.
//     */
//    @FormUrlEncoded()
//    @POST("v1/$LOCAL_SUFFIX/user/logout")
//    fun logOutSNSUser(@Field("uuid") uuid: String): CustomCall<Void>
//
//    /**
//     * This method used to get language.
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/language")
//    fun getLanguage(): Single<Language>
//
//    /**
//     * This method used to put language.
//     */
//    @FormUrlEncoded
//    @PUT("v1/$LOCAL_SUFFIX/user/language")
//    fun putLanguage(@Field("language") language: String): CustomCall<Void>
//
//    /**
//     * This method used to get region.
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/region")
//    fun getRegion(): Single<Region>
//
//    /**
//     * This method used to put region.
//     */
//    @FormUrlEncoded
//    @PUT("v1/$LOCAL_SUFFIX/user/region")
//    fun putRegion(@Field("region") region: String): CustomCall<Void>
//
//    /**
//     * This method used to post reason when delete outfit
//     */
//    @FormUrlEncoded()
//    @POST("v1/$LOCAL_SUFFIX/outfit/detail/report")
//    fun reportOutfitDetail(@Field("id_outfit") idOutfit: Int, @Field("reason") reason: Int): CustomCall<Void>
//
//    /**
//     * This method used to post reason when delete comment
//     */
//    @FormUrlEncoded()
//    @POST("v1/$LOCAL_SUFFIX/outfit/{id_outfit}/comment/report")
//    fun reportOutfitComment(
//        @Path("id_outfit") idOutfit: Int, @Field("id_outfit_comment") idComment: Int,
//        @Field("reason") reason: Int
//    ): CustomCall<Void>
//
//    /**
//     * This method used to post user report outfit
//     */
//    @FormUrlEncoded()
//    @POST("v1/$LOCAL_SUFFIX/user/{id_user_app}/profile/report")
//    fun reportUser(@Path("id_user_app") idUserApp: Int, @Field("reason") reason: Int): CustomCall<Void>
//
//    /**
//     * This method used to block user
//     */
//    @POST("v1/$LOCAL_SUFFIX/user/{id_blocked_user}/block")
//    fun blockUser(@Path("id_blocked_user") idUserApp: Int): CustomCall<Void>
//
//    /**
//     * This method used to unblock user
//     */
//    @PATCH("v1/$LOCAL_SUFFIX/user/{id_blocked_user}/block")
//    fun unBlockUser(@Path("id_blocked_user") idUserApp: Int): CustomCall<Void>
//
//    /**
//     * This method used to get block users
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/block")
//    fun getBlockUsers(): Single<BlockUserResponse>
//
//    /**
//     * This method used to get term-policy
//     */
//    @GET("v2/$LOCAL_SUFFIX/term-policy/info")
//    fun getTermPolicy(): Single<TermPolicyResponse>
//
//    /**
//     * This method used to put term-policy
//     */
//    @PUT("v1/$LOCAL_SUFFIX/term-policy/info")
//    fun putTermPolicy(): Single<TermPolicyResponse>
//
//    /**
//     * This method get list location filter
//     */
//    @GET("v1/$LOCAL_SUFFIX/location/filter/search")
//    fun getListLocationFilters(
//        @Query("location_key_word", encoded = true) locationKeyWord: String,
//        @Query("category", encoded = true) category: Int,
//        @Query("result_limit", encoded = true) resultLimit: Int
//    ): CustomCall<LocationFilterResponse>
//
//    @GET("v1/$LOCAL_SUFFIX/location/top")
//    fun getPopularLocations(
//        @Query("category", encoded = true) category: Int,
//        @Query("result_limit", encoded = true) resultLimit: Int
//    ): Single<LocationFilterResponse>
//
//    /**
//     * This method get item detail
//     */
//    @GET("v1/$LOCAL_SUFFIX/item/{id_pf_item}/detail")
//    fun getItemDetail(@Path("id_pf_item") idPfItem: Int): Single<ItemDetail>
//
//    /**
//     * This method used to star up item.
//     *
//     * @param id_pf_item
//     */
//    @POST("v1/$LOCAL_SUFFIX/item/{id_pf_item}/star")
//    fun itemDetailStarCountUp(@Path("id_pf_item") id: Int): Single<StarCountResponse>
//
//    /**
//     * This method used to star down item.
//     *
//     * @param id_pf_item
//     */
//    @DELETE("v1/$LOCAL_SUFFIX/item/{id_pf_item}/star")
//    fun itemDetailStarCountDown(@Path("id_pf_item") id: Int): Single<StarCountResponse>
//
//    /**
//     * This method used to get a item detail similar outfit list
//     */
//    @GET("v1/$LOCAL_SUFFIX/item/{id_pf_item}/outfit_list")
//    fun getItemDetailSimilarOutfits(
//        @Path("id_pf_item") idPfItem: Int,
//        @Query("result_limit") resultLimit: Int,
//        @Query("page") page: Int
//    ): Single<SimilarOutfitResponse>
//
//    /**
//     * This method use to get save item
//     */
//    @GET("v1/$LOCAL_SUFFIX/user/saved-items")
//    fun getSavedItems(@Query("result_limit") resultLimit: Int, @Query("page") page: Int): Single<SaveItemsResponse>
//
//    /**
//     * This method use get outfit visual search
//     */
//    @POST("v1/$LOCAL_SUFFIX/outfit/visual-search")
//    fun getOutfitVisualSearch(@Body outfit: OutfitVisualSearchBody, @Query("style_gender") styleGender: Int?): Single<OutfitVisualSearchResponse>
}
