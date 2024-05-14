package orders.db;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import orders.model.Item;
import orders.model.Order;
import orders.model.Product;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderDBImp implements OrderDB {
    private final List<Order> orders = new ArrayList<>();
    private final List<Item> cart = new ArrayList<>();
    int orderId = 0;
    private List<Product> products = null;

    @Override
    public List<Product> getProducts() {
        //  try {
        if (products == null) {
            String json = "[{\"pid\":\"10167771016\",\"name\":\"【清华】Java从入门到精通(第7七版) java语言程序设计电脑编程基础计算机软件开发教程JAVA编程入门零基础自学书籍javascript Java入门经典\",\"price\":35.8,\"category\":\"Java\",\"img\":\"https://img14.360buyimg.com/n7/jfs/t1/176588/14/42623/190268/65defb2cFde50bf31/cef8c393c8e3a58b.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"14067396\",\"name\":\"Java从入门到精通（第7版）（软件开发视频大讲堂） 团购50册以上优惠咨询：（010）89110538\",\"price\":44.9,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/92880/40/37274/91485/64b0f246F28539098/16f93df907ef0d29.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025351482843\",\"name\":\"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 亮光银 490：165-175 CM\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img13.360buyimg.com/n7/jfs/t1/135151/29/37124/55491/65d5a4a2Fa0a25fdd/063474a396f80b92.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025252072678\",\"name\":\"JAVA佳沃御夫座auriga24款公路车自行车骑行运动健身训练成人变速学生 亮光钛 480mm\",\"price\":2999.0,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/220971/22/38958/52867/6604e196F8f297921/7fa5ab987bce9c50.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025219565749\",\"name\":\"JAVA佳沃维洛克3公路车2024铝合金16变速轻量弯把油压碟刹桶轴自行车 亮光银（24款\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/100457/28/46675/53278/65b865afFf0a0c488/23d80d5dfd4d6cda.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025351482843\",\"name\":\"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 亮光银 530：175-185 CM\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img10.360buyimg.com/n7/jfs/t1/135151/29/37124/55491/65d5a4a2Fa0a25fdd/063474a396f80b92.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025270540368\",\"name\":\"JAVA佳沃鱼雷6top24款公路车自行车骑行运动健身训练成人学生7120r5 亮光白（蓝图RX） M\",\"price\":3999.0,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/241967/6/3426/80856/65a602d5F75b6f163/6cc99fb6d33ad8d2.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025082639607\",\"name\":\"佳沃JAVA/24款佳沃御夫座AURIGA桶轴公路车油压碟刹18变速竞赛自行车 黑色 S\",\"price\":2999.0,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/236497/37/6269/69367/6570acb0F6afa430c/3e3d0201b4db6f26.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025351482843\",\"name\":\"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 消光黑灰 490：165-175 CM\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img14.360buyimg.com/n7/jfs/t1/101352/26/47075/62183/65daf5f6F06acccc0/3950e13ecaf1e1df.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025053462714\",\"name\":\"佳沃JAVA公路自行车铝合金公路车全油碟刹18变速弯把单车御夫座AURIGA 黑色 XS（身高:150-168CM） 18变速\",\"price\":2999.0,\"category\":\"Java\",\"img\":\"https://img10.360buyimg.com/n7/jfs/t1/221998/1/33141/56183/655c595dFb5ebe1e0/6468dfa6e29c2182.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025219565749\",\"name\":\"JAVA佳沃维洛克3公路车2024铝合金16变速轻量弯把油压碟刹桶轴自行车 消光黑灰（24款\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/95914/9/47072/69909/65b865a8Fcdf3d5a6/60334855ff77d9b0.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10020900924095\",\"name\":\"拍拍 【二手8成新】Java从入门到精通第5版 明日科技 清华大学出版社 9787302517597 正版旧书,品质现货,全国多仓,择优速发!2件2元运费3件包邮(新藏琼蒙除外)\",\"price\":9.99,\"category\":\"Java\",\"img\":\"https://img10.360buyimg.com/n7/jfs/t1/172108/10/3973/124669/60765bbcE50292df2/364e3a6432332135.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025270540368\",\"name\":\"JAVA佳沃鱼雷6top24款公路车自行车骑行运动健身训练成人学生7120r5 亮光白（蓝图RX） XS\",\"price\":3999.0,\"category\":\"Java\",\"img\":\"https://img14.360buyimg.com/n7/jfs/t1/241967/6/3426/80856/65a602d5F75b6f163/6cc99fb6d33ad8d2.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025249749786\",\"name\":\"JAVA佳沃维洛克32024款公路车变速自行车骑行运动健身训练成人变速 银色（蓝图R3） L\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img13.360buyimg.com/n7/jfs/t1/21033/28/20826/81059/659d28d4Fe337357c/df668e21f90fc5c4.png.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10023916882555\",\"name\":\"佳沃JAVA鱼雷6公路自行车双油压碟刹桶轴24速弯把破风单车SILURO6-TOP 【桶轴版】亮光黑 【500 M】建议身高：171-177(cm) 【禧玛诺R7120】-24速\",\"price\":5499.0,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/233629/9/2459/58492/654df511F31e8b056/4a5e6ad0e913b6c1.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025053462714\",\"name\":\"佳沃JAVA公路自行车铝合金公路车全油碟刹18变速弯把单车御夫座AURIGA 钛色 XS（身高:150-168CM） 18变速\",\"price\":2999.0,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/238395/4/12034/58692/65a7a2d2F27641ca3/0266133061e6a093.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10024491803295\",\"name\":\"佳沃JAVA/佳沃SILURO6-TOP桶轴公路车鱼雷6油压碟刹24变速竞赛自行车 亮光白 XL（183-195 CM 7120套件） 24变速\",\"price\":4999.0,\"category\":\"Java\",\"img\":\"https://img12.360buyimg.com/n7/jfs/t1/90856/32/45661/60763/65db1475Fe4b433fd/f25587bc1996b01e.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025351482843\",\"name\":\"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 消光黑灰 460：155-170 CM\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img13.360buyimg.com/n7/jfs/t1/101352/26/47075/62183/65daf5f6F06acccc0/3950e13ecaf1e1df.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10023360214931\",\"name\":\"JAVA 公路自行车双碟刹碳纤维公路车18变速弯把男女款单车赛车鱼雷3 白色青春标准版 18速 53#身高181-187cm 700C\",\"price\":2999.0,\"category\":\"Java\",\"img\":\"https://img12.360buyimg.com/n7/jfs/t1/235796/33/5256/91346/6568648bF0101ee05/d59f29da116f2875.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025178119265\",\"name\":\"佳沃JAVA/佳沃SILURO6-R5桶轴公路车鱼雷6油压碟刹18变速竞赛自行车 亮光白 XL（183-195 CM）\",\"price\":3298.0,\"category\":\"Java\",\"img\":\"https://img10.360buyimg.com/n7/jfs/t1/235328/23/8799/52977/65856ce3F77b81c31/c0e64727734f55d8.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025219565749\",\"name\":\"JAVA佳沃维洛克3公路车2024铝合金16变速轻量弯把油压碟刹桶轴自行车 消光黑灰（23款\",\"price\":2099.0,\"category\":\"Java\",\"img\":\"https://img14.360buyimg.com/n7/jfs/t1/243743/39/1902/59834/65950bd3F1c30f002/ff6de5a042a93df4.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025408555527\",\"name\":\"佳沃JAVA/佳沃RONDA 16速 喜玛偌r2000 全内走铝合金车架公路车 白蓝 XS 162-169cm\",\"price\":2099.0,\"category\":\"Java\",\"img\":\"https://img13.360buyimg.com/n7/jfs/t1/237729/34/13408/84561/65ec091fFb9b33bd1/525c6479fae888be.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025219565749\",\"name\":\"JAVA佳沃维洛克3公路车2024铝合金16变速轻量弯把油压碟刹桶轴自行车 消光黑灰（24款\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img12.360buyimg.com/n7/jfs/t1/95914/9/47072/69909/65b865a8Fcdf3d5a6/60334855ff77d9b0.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025178119265\",\"name\":\"佳沃JAVA/佳沃SILURO6-R5桶轴公路车鱼雷6油压碟刹18变速竞赛自行车 亮光香槟 XL（183-195 CM）\",\"price\":3298.0,\"category\":\"Java\",\"img\":\"https://img10.360buyimg.com/n7/jfs/t1/238490/31/804/58364/65856ce3F0e0d2461/43366c16c9a8418d.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10023360214931\",\"name\":\"JAVA 公路自行车双碟刹碳纤维公路车18变速弯把男女款单车赛车鱼雷3 黑色青春标准版 18速 50#身高173-180cm 700C\",\"price\":2999.0,\"category\":\"Java\",\"img\":\"https://img11.360buyimg.com/n7/jfs/t1/140848/11/37671/106340/64bcec0bF3f200138/b856f496ad976b20.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10021335437051\",\"name\":\"佳沃（JAVA） 公路自行车鱼雷3代碟刹22变速弯把男女碳纤维前叉男女成人赛车 3代黑色18速青春 18速 50#建议173-178CM 700C\",\"price\":2999.0,\"category\":\"Java\",\"img\":\"https://img10.360buyimg.com/n7/jfs/t1/184599/40/29087/106340/64bce61cF877a781c/b8a43596cf2c052b.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025270540368\",\"name\":\"JAVA佳沃鱼雷6top24款公路车自行车骑行运动健身训练成人学生7120r5 亮光白（蓝图RX） L\",\"price\":3999.0,\"category\":\"Java\",\"img\":\"https://img12.360buyimg.com/n7/jfs/t1/241967/6/3426/80856/65a602d5F75b6f163/6cc99fb6d33ad8d2.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025351482843\",\"name\":\"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 消光黑灰 510：172-182 CM\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img10.360buyimg.com/n7/jfs/t1/101352/26/47075/62183/65daf5f6F06acccc0/3950e13ecaf1e1df.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10025351482843\",\"name\":\"佳沃JAVA公路自行车铝合金全油压碟刹变速桶轴男女弯把赛车佳沃VELOCE 亮光银 460：155-170 CM\",\"price\":2299.0,\"category\":\"Java\",\"img\":\"https://img14.360buyimg.com/n7/jfs/t1/135151/29/37124/55491/65d5a4a2Fa0a25fdd/063474a396f80b92.jpg.avif\",\"stock\":1,\"quantity\":500},{\"pid\":\"10023916882555\",\"name\":\"佳沃JAVA鱼雷6公路自行车双油压碟刹桶轴24速弯把破风单车SILURO6-TOP 【桶轴版】亮光香槟 【450 XS】建议身高：158-164(cm 【2024款-蓝图】-24速\",\"price\":4499.0,\"category\":\"Java\",\"img\":\"https://img13.360buyimg.com/n7/jfs/t1/184773/29/35791/60596/64c33be5F29207ef5/5de8f33833208547.jpg.avif\",\"stock\":1,\"quantity\":500}]";
            Type productListType = new TypeToken<List<Product>>() {
            }.getType();
            products = new Gson().fromJson(json, productListType);
        }
        return products;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getPid().equals(productId))
                return p;
        }
        return null;
    }

    @Override
    public void saveOrder() {
        orderId++;
        Order newOrder = new Order(orderId, cart);
        orders.add(newOrder);
        cart.clear();
    }

    @Override
    public Order getOrder(int orderId) {
        for (Order order : orders)
            if (order.getOrderId() == orderId)
                return order;
        return null;
    }

    @Override
    public void changeItem(String productId, int deltaAmount) {
        for (Item item : cart) {
            if (Objects.equals(item.getProduct().getPid(), productId)) {
                item.setAmount(item.getAmount() + deltaAmount);
                if (item.getAmount() == 0)
                    cart.remove(item);
                return;
            }
        }
        if (deltaAmount > 0)
            cart.add(new Item(getProduct(productId), deltaAmount));
    }

    @Override
    public Item getItem(String productId) {
        for (Item item : cart) {
            if (Objects.equals(item.getProduct().getPid(), productId))
                return item;
        }
        return null;
    }
}
