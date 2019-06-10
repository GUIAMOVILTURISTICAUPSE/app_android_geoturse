var World = {
    loaded: false,
    dinoSettings: {
        foca: {
            scale: 1.0
        },
        hueso: {
            scale: 0.2
        },
        sable: {
            scale: 0.3
        }
        /*,
        tyrannosaurus: {
            scale: 0.4
        }*/
    },

    init: function initFn() {
        this.createOverlays();
    },

    createOverlays: function createOverlaysFn() {
        this.targetCollectionResource = new AR.TargetCollectionResource("assets/qrsmarcador.wtc", {
            onError: World.onError
        });

        this.tracker = new AR.ImageTracker(this.targetCollectionResource, {
            maximumNumberOfConcurrentlyTrackableTargets: 3, // a maximum of 5 targets can be tracked simultaneously
            extendedRangeRecognition: AR.CONST.IMAGE_RECOGNITION_RANGE_EXTENSION.OFF,
            onTargetsLoaded: World.showInfoBar,
            onError: World.onError
        });


        new AR.Model("assets/model/hueso.wt3");
        new AR.Model("assets/model/sable.wt3");
        new AR.Model("assets/model/foca.wt3");
        //new AR.Model("assets/models/tyrannosaurus.wt3");


        this.dinoTrackable = new AR.ImageTrackable(this.tracker, "*", {
            onImageRecognized: function(target) {

                var model = new AR.Model("assets/model/" + target.name + ".wt3", {
                    scale: World.dinoSettings[target.name].scale,
                    rotate: {
                        z: 180
                    },
                    onError: World.onError
                });

                /* Adds the model as augmentation for the currently recognized target. */
                this.addImageTargetCamDrawables(target, model);

                World.hideInfoBar();
            },
            onError: World.onError
        });
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