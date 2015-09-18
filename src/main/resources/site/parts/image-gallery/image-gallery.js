var portal = require('/lib/xp/portal');
var thymeleaf = require('/lib/xp/thymeleaf');
var contentSvc = require('/lib/xp/content');

// Handle GET request
exports.get = handleGet;

function handleGet(req) {
    var me = this;

    function renderView() {
        var component = portal.getComponent();
        var folderId = component.config.imageFolder;

        var maxImage = component.config.maxImages ? component.config.maxImages : 10;

        var result = contentSvc.getChildren({
            key: folderId,
            start: 0,
            count: maxImage,
            sort: '_modifiedTime ASC'
        });

        log.info("Result" + JSON.stringify(result));

        var hits = result.hits;

        var images = [];

        // Loop through the contents and extract the needed data
        for (var i = 0; i < hits.length; i++) {
            var image = {};
            image.src = portal.imageUrl({
                id: hits[i]._id,
                scale: 'block(330,300)',
                format: 'jpeg'
            });

            log.info("Image" + JSON.stringify(image));

            images.push(image);
        }

        var params = {
            imageList: images
        };

        var view = resolve('image-gallery.html');
        var body = thymeleaf.render(view, params);

        return {
            contentType: 'text/html',
            body: body,
            pageContributions: {
                headEnd: [
                    '<link rel="stylesheet" href="' + portal.assetUrl({path: 'css/photoswipe.css'}) + '" type="text/css" />',
                    '<link rel="stylesheet" href="' + portal.assetUrl({path: 'css/default-skin/default-skin.css'}) + '" type="text/css" />',
                    '<link rel="stylesheet" href="' + portal.assetUrl({path: 'css/photoswipe.min.js'}) + '" type="text/css" />',
                    '<script src="' + portal.assetUrl({path: 'js/photoswipe-ui-default.min.js'}) + '"></script>'
                ]
            }
        };
    }

    return renderView();
}