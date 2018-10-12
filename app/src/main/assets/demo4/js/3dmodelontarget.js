var World = {
    loaded: false,
    rotating: false,

    init: function initFn() {
        this.createOverlays();
    },

    createOverlays: function createOverlaysFn() {
              this.targetCollectionResource = new AR.TargetCollectionResource("assets/tracker.wtc", {
            onError: World.onError
        });

         this.tracker = new AR.ImageTracker(this.targetCollectionResource, {
            onTargetsLoaded: World.showInfoBar,
            onError: World.onError
        });

        this.modelCar = new AR.Model("assets/car.wt3", {
            onLoaded: World.showInfoBar,
            onError: World.onError,
            scale: {
                x: 0.045,
                y: 0.045,
                z: 0.045
            },
            translate: {
                x: 0.0,
                y: 0.05,
                z: 0.0
            },
            rotate: {
                z: -25
            }
        });

        this.trackable = new AR.ImageTrackable(this.tracker, "*", {
            drawables: {
                cam: [this.modelCar]
            },
            onImageRecognized: World.hideInfoBar,
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