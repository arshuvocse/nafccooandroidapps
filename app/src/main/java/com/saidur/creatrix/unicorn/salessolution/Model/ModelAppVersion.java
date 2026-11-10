package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelAppVersion {
    private int AppVersionId;
    private int Version;
    private String VersionName;
    private String DownloadUrl;
    private boolean IsForceUpdate;

    public ModelAppVersion() {
    }

    public int getAppVersionId() {
        return AppVersionId;
    }

    public void setAppVersionId(int appVersionId) {
        AppVersionId = appVersionId;
    }

    public int getVersion() {
        return Version;
    }

    public void setVersion(int version) {
        Version = version;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public String getDownloadUrl() {
        return DownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        DownloadUrl = downloadUrl;
    }

    public boolean isForceUpdate() {
        return IsForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        IsForceUpdate = forceUpdate;
    }
}
