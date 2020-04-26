use(function () {

    var Constants = {
        REDIRECT_TARGET: "redirectTarget"
    };

    var redirectTarget = this.page.getProperties().get(Constants.REDIRECT_TARGET, "");
    var redirectPath = this.page.getProperties().get(Constants.REDIRECT_TARGET);
    return {
        redirectTarget: redirectTarget,
        redirectPath: redirectPath
    };
});
