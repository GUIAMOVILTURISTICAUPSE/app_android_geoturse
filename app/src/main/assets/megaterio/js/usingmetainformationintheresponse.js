var World = {
    loaded: false,
    tracker: null,
    cloudRecognitionService: null,

    init: function initFn() {
        this.createTracker();
        this.createOverlays();
    },

    createTracker: function createTrackerFn() {
    alert("Reconocimiento de la coleccion");
        this.cloudRecognitionService = new AR.CloudRecognitionService(
          "6f19a9fd0be6fd7f4ba2033b59d4d2d2",
          "5cc889e4393b2d3ca160696c", {
                onInitialized: World.trackerLoaded,
                onError: World.onError
            }
        );

        World.tracker = new AR.ImageTracker(this.cloudRecognitionService, {
            onError: World.onError
        });
    },

    startContinuousRecognition: function startContinuousRecognitionFn(interval) {
        alert("funcion starcontinuos");
        this.cloudRecognitionService.startContinuousRecognition(interval, this.onInterruption, this.onRecognition, this.onRecognitionError);
    },

    createOverlays: function createOverlaysFn() {
    alert("funcion overlays");
      /*  this.bannerImg = new AR.ImageResource("assets/foca.jpg", {
            onError: World.onError
        });
        this.bannerImgOverlay = new AR.ImageDrawable(this.bannerImg, 0.8, {
            translate: {
                y: 0.6
            }
        });
*/
        this.orderNowButtonImg = new AR.ImageResource("assets/orderNowButton.png", {
            onError: World.onError
        });
        this.orderNowButtonOverlay = new AR.ImageDrawable(this.orderNowButtonImg, 0.3, {
            translate: {
                y: -0.6
            }
        });
    },
      onRecognition: function onRecognitionFn(recognized, response) {

        if (recognized) {
            /* Clean Resources from previous recognitions. */
            alert("funcionon recognition en la condicion verdadera ");
            if (World.wineLabel !== undefined) {
                World.wineLabel.destroy();
            }

            if (World.wineLabelOverlay !== undefined) {
                World.wineLabelOverlay.destroy();
            }


      /*   World.wineLabel = new AR.ImageResource("foca.jpg", {
             onError: World.onError
         });
         World.wineLabelOverlay = new AR.ImageDrawable(World.wineLabel, 0.3, {
             translate: {
                 x: -0.5,
                 y: -0.6
             },
             zOrder: 1
         });
*/
        World.objeto3d = new AR.Model("assets/"+ response.targetInfo.name +".wt3", {
             onLoaded: World.showInfoBar,
             onError: World.onError,
             scale: {
                 x: 2,
                 y: 2,
                 z: 2
             },
             translate:{
             x:0,
             y: 1
             },
                zOrder: 3
             });


            World.wineName = new AR.Label(response.metadata.name, 0.06, {
                translate: {
                    y: 0.72
                },
                zOrder: 2
            });


            if (World.wineLabelAugmentation !== undefined) {
                World.wineLabelAugmentation.destroy();
            }


            World.orderNowButtonOverlay.onClick = function() {
                AR.context.openInBrowser(response.metadata.shop_url);
            };


            World.wineLabelAugmentation = new AR.ImageTrackable(World.tracker, response.targetInfo.name, {
                drawables: {
                    cam: [ World.objeto3d, World.wineName, World.orderNowButtonOverlay]
                },
                onError: World.onError
            });

            World.hideInfoBar();
        }
    },

    onRecognitionError: function onRecognitionErrorFn(errorCode, errorMessage) {
        World.cloudRecognitionService.stopContinuousRecognition();
        alert("error code: " + errorCode + " error message: " + JSON.stringify(errorMessage));
    },


    onInterruption: function onInterruptionFn(suggestedInterval) {
        World.cloudRecognitionService.stopContinuousRecognition();
        World.startContinuousRecognition(suggestedInterval);
    },

    trackerLoaded: function trackerLoadedFn() {
        World.startContinuousRecognition(750);
        World.showInfoBar();
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