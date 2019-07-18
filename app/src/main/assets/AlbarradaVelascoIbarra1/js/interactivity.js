var World = {
    rotating: false,
    loaded: false,
    tracker: null,
    cloudRecognitionService: null,

    init: function initFn() {
      this.createTracker();
        this.createOverlays();
    },

    createTracker: function createTrackerFn() {
        this.cloudRecognitionService = new AR.CloudRecognitionService(
            "6f19a9fd0be6fd7f4ba2033b59d4d2d2",
            "5cc889e4393b2d3ca160696c",
             {
                onInitialized: World.showInfoBar,
                onError: World.onError
            }
        );
        this.tracker = new AR.ImageTracker(this.cloudRecognitionService, {
            onError: World.onError
        });
    },

    createOverlays: function createOverlaysFn() {
 /*           this.targetCollectionResource = new AR.TargetCollectionResource("assets/tracker.wtc", {
            onError: World.onError
        });
        this.tracker = new AR.ImageTracker(this.targetCollectionResource, {
            onTargetsLoaded: World.showInfoBar,
            onError: World.onError
        });
*/
        alert("Aqui se crea el overlay basico");
        console.log("Aqui se crea el overlay basico");

        this.bannerImg = new AR.Model("assets/car.wt3", {
            onClick: World.toggleAnimateModel,
            onLoaded: World.showInfoBar,
            onError: World.onError,
            scale: {
                x: 0.0,
                y: 0.0,
                z: 0.0
            }/*,
            translate: {
                x: 0.0,
                y: 0.05,
                z: 0.0
            },
            rotate: {
                z: -25
            }*/
        });

         var myJSONBannerImg = JSON.stringify(this.bannerImg);
        alert("myJSONBannerImg: " + myJSONBannerImg);
        console.log("myJSONBannerImg: " + myJSONBannerImg);

        this.appearingAnimation = this.createAppearingAnimation(this.bannerImg, 0.045);

        this.rotationAnimation = new AR.PropertyAnimation(this.bannerImg, "rotate.z", -25, 335, 10000);
           alert("Imagen del boton de rotacion  ");
        var imgRotate = new AR.ImageResource("assets/rotateButton.png", {
            onError: World.onError
        });
        var buttonRotate = new AR.ImageDrawable(imgRotate, 0.2, {
            translate: {
                x: 0.35,
                y: 0.45
            },
            onClick: World.toggleAnimateModel
        });

          alert("paso las animaciones ");
       },


       onRecognition: function onRecognitionFn(recognized, response) {
        if (recognized) {
              var myJSON = JSON.stringify(response);
               console.log("JSon en la consola:" + myJSON);

            console.log("Reconocio 1");
            alert("Reconocio 1");

            if (World.wineLabel !== undefined) {
                World.wineLabel.destroy();
            }

            if (World.wineLabelOverlay !== undefined) {
                World.wineLabelOverlay.destroy();
            }

             console.log("Reconocio 2:" + response.targetInfo.name);
              alert(response.targetInfo.name);

            World.wineLabel = new AR.ImageResource("assets/" + response.targetInfo.name + ".jpg"});

             console.log("Reconocio 3:" + World.wineLabel);
                        alert(World.wineLabel);
            World.wineLabelOverlay = new AR.ImageDrawable(World.wineLabel, 0.3, {
                translate: {
                    x: -0.5,
                    y: -0.6
                },
                zOrder: 1
            });

            if (World.wineLabelAugmentation !== undefined) {
            console.log("Ok. WineLabelAugmentation Definido y se borrarse como el Crush");
             alert("Ok. WineLabelAugmentation Definido y se borrarse como el Crush");
                World.wineLabelAugmentation.destroy();
            }


            World.wineLabelAugmentation = new AR.ImageTrackable(World.tracker, response.targetInfo.name, {
                drawables: {
                    cam: [World.bannerImgOverlay, World.wineLabelOverlay]
                },
                onError: World.error
            });

            World.hideInfoBar();
        } else {

            document.getElementById('errorMessage').innerHTML =
                "<div class='errorMessage'>Recognition failed, please try again!</div>";

            setTimeout(function() {
             alert("Guayando");
                var e = document.getElementById('errorMessage');
                e.removeChild(e.firstChild);
            }, 3000);
        }
      },

    onRecognitionError: function onRecognitionError(errorCode, errorMessage) {
        alert("error code: " + errorCode + " error message: " + JSON.stringify(errorMessage));
    },

    onRecognitionError: function onRecognitionError(errorCode, errorMessage) {
        alert("error code: " + errorCode + " error message: " + JSON.stringify(errorMessage));
    },

    createAppearingAnimation: function createAppearingAnimationFn(model, scale) {

        var sx = new AR.PropertyAnimation(model, "scale.x", 0, scale, 1500, {
            type: AR.CONST.EASING_CURVE_TYPE.EASE_OUT_ELASTIC
        });
        var sy = new AR.PropertyAnimation(model, "scale.y", 0, scale, 1500, {
            type: AR.CONST.EASING_CURVE_TYPE.EASE_OUT_ELASTIC
        });
        var sz = new AR.PropertyAnimation(model, "scale.z", 0, scale, 1500, {
            type: AR.CONST.EASING_CURVE_TYPE.EASE_OUT_ELASTIC
        });

        return new AR.AnimationGroup(AR.CONST.ANIMATION_GROUP_TYPE.PARALLEL, [sx, sy, sz]);
    },

    appear: function appearFn() {
        World.hideInfoBar();
        /* Resets the properties to the initial values. */
        World.resetModel();
        World.appearingAnimation.start();
    },

    resetModel: function resetModelFn() {
        World.rotationAnimation.stop();
        World.rotating = false;
        World.bannerImg.rotate.z = -25;
    },

    toggleAnimateModel: function toggleAnimateModelFn() {
        if (!World.rotationAnimation.isRunning()) {
            if (!World.rotating) {
                /* Starting an animation with .start(-1) will loop it indefinitely. */
                World.rotationAnimation.start(-1);
                World.rotating = true;
            } else {
                /* Resumes the rotation animation */
                World.rotationAnimation.resume();
            }
        } else {
            /* Pauses the rotation animation */
            World.rotationAnimation.pause();
        }

        return false;
    },

    onError: function onErrorFn(error) {
        alert(error)
    },

    hideInfoBar: function hideInfoBarFn() {
        document.getElementById("infoBox").style.display = "none";
    },

    showInfoBar: function worldLoadedFn() {
        document.getElementById("infoBox").style.display = "table";
        document.getElementById("loadingMessage").style.display = "none";
    }
};

World.init();