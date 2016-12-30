package fast.glibrary.data;

import java.util.ArrayList;
import java.util.Random;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/30 14:59
 * 修改人：Administrator
 * 修改时间：2016/12/30 14:59
 * 修改备注：
 */
public class DataFatroty {
    private static ArrayList<String> images = new ArrayList<>();

    static {
        images.add("http://image.tianjimedia.com/uploadImages/2016/358/30/JZF8YJV8HJ88.jpg");
        images.add("http://image.tianjimedia.com/uploadImages/2016/365/48/13E564CQ4I4H.jpg");
        images.add("http://img.youguoquan.com/uploads/magazine/cover/6ca1703aaa726f6442fdbe3d3a1c4cd3_cover_web_l.jpg");
        images.add("http://img.youguoquan.com/uploads/magazine/cover/0d77de92aca97699d00e20d1417cd01d_cover_web_l.jpg");
        images.add("http://img.hb.aicdn.com/57c16039c851d1bec23524bf1a8520bff323ac6592f1d-FQcZzf_fw658");
        images.add("http://img.hb.aicdn.com/ffcf3ae82a2abf845cb71359145c0a4813cae8fe322f7-L3o3uE_fw658");
    }

    public static String getImage() {
        Random random = new Random();
        return images.get(random.nextInt(images.size()));
    }
}
