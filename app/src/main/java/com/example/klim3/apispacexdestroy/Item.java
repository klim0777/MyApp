package com.example.klim3.apispacexdestroy;


public class Item {

    public String mRocketName,
                  mLaunchDate,
                  mDetails,
                  mImage,
                  mArticleLink;

    public String getRocketName(){
        return mRocketName;
    }

    public String getLaunchDate(){
        return  mLaunchDate;
    }

    public String getDetails(){
        return mDetails;
    }

    public String getImage() {
        return mImage;
    }

    public String getArticleLink(){
        return mArticleLink;
    }


    public void setRocketName(String rocketName){
        mRocketName = rocketName;
    }

    public void setLaunchDate(String launchDate){
        mLaunchDate = launchDate;
    }

    public void setDetails(String details){
        mDetails = details;
    }

    public void setImage(String image) {
        mImage= image;
    }

    public void setArticleLink(String articleLink){
        mArticleLink = articleLink;
    }

}
