package ee.hm.dop.model.enums;

import ee.hm.dop.service.files.PictureCutter;

import static ee.hm.dop.model.enums.Size.ThumbnailcreatingStrategy.CREATE_USING_WIDTH_AND_HEIGHT;
import static ee.hm.dop.model.enums.Size.ThumbnailcreatingStrategy.CREATE_USING_WIDTH_ONLY;


/**\
 * cardSize_screenSize1_screenSize2...
 */
public enum Size {
    SM(CREATE_USING_WIDTH_AND_HEIGHT, PictureCutter.SM_THUMBNAIL_WIDTH, PictureCutter.SM_THUMBNAIL_HEIGHT),
    SM_XS_XL(CREATE_USING_WIDTH_AND_HEIGHT, PictureCutter.SM_XS_XL_THUMBNAIL_WIDTH, PictureCutter.SM_XS_XL_THUMBNAIL_HEIGHT),
    LG(CREATE_USING_WIDTH_ONLY, PictureCutter.LG_THUMBNAIL_WIDTH),
    LG_XS(CREATE_USING_WIDTH_ONLY, PictureCutter.LG_XS_THUMBNAIL_WIDTH);

    private Integer width;
    private Integer height;
    private ThumbnailcreatingStrategy strategy;

    Size (ThumbnailcreatingStrategy strategy, Integer width){
        this.strategy = strategy;
        this.width = width;
    }

    Size (ThumbnailcreatingStrategy strategy, Integer width, Integer height){
        this.strategy = strategy;
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public boolean createUsingWidthAndHeight(){
        return this.strategy == CREATE_USING_WIDTH_AND_HEIGHT;
    }

    public enum  ThumbnailcreatingStrategy {
        CREATE_USING_WIDTH_ONLY, CREATE_USING_WIDTH_AND_HEIGHT;
    }

}
