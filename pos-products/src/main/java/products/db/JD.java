package products.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;
import products.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JD implements PosDB{

	@Autowired
	private CartDb cartDb;
	@Autowired
	private ProductDb productDb;

	private List<Product> products = null;
	private final Long only_cart_id = 1L;
	static String cookieString = "__jdv=122270672%7Cdirect%7C-%7Cnone%7C-%7C1714291718453; mba_muid=17142917184521525055223; wlfstk_smdl=vp548udmab6gqe4m0r5lu1x8tpy3u7yp; __jdu=17142917184521525055223; TrackID=1_hEc76PecMLYoICfPCkj41iMm1PcxuDHHjNAAhZbZcZXYhvNBDsBbO5XttAYP2Zjhn_RbNSYjLbmOMUlo8Zdl0A6KX2NSvrFvazCJth7O_h5jiPH14xkVOwir63IUMV4VqhSlJbiFl5HUFQ9tiiMRg; thor=5B72BCB4F4F46B3DAF958302E3A3AC4D06BD791A1B1168252F0E189B05B3D54E1F3D0288B657FBF1D9F087FDC4D2F726391383EE31B719985B6C971DF52B6DB51C5D1A4A7158FC2E5947B1C2B28D97FC305B69E3A11DF4A51C28F9C2360825A3C8C890F7D11213CF5B3C50859EECBBCDBC18F5135EA8AB9F3B4CE099C3707E4EBE2671CA4F52A6AECB0CA9D9FA8DEB3B1303D3543E3E8B6DDF90FDB3FF342F82; flash=2_2SlANBVIGU-PRp4fJlNKuNqUnUk_qlAzj1QdNfjjI58xqBWcqoqL7jN2wurSLhme13p3PGRN0cqnPvV2mGIOgju-uLdZyh0xIlW57RyERoEeBx6BhBkVu1ejexb4yAeXUMIMLvtxdIeQW2cRCN22Qe**; pinId=wY9kFRrGKMP6AnM-05Yh7LV9-x-f3wj7; pin=jd_6555852cd0902; unick=jd_136826yge; ceshi3.com=000; _tp=ph8CaXqJRWkVtoYU2wwZGlPO9KkCvvSjkQhek9elYVA%3D; _pst=jd_6555852cd0902; ipLoc-djd=12-904-0-0; shshshfpa=27385517-933f-1e1f-0120-b2c7fdc578df-1714291824; shshshfpx=27385517-933f-1e1f-0120-b2c7fdc578df-1714291824; __jdc=143920055; xapieid=jdd03LWP4N4353YBIRVSKYG5OJTAYE53IO7OBOIOY6BEAE6E2YG4TMEWOOMASARQ5YNZHWNQPFOFWFUDMRNSEZYP2P6JN7UAAAAMPEPAVMCQAAAAACZEUCDGL2OOUCQX; shshshfpb=BApXcSsDJIOpAzvsHYXjwq7Pp27BGA8kFBlZoHwto9xJ1Msw_M4C2; rkv=1.0; areaId=12; jsavif=1; jsavif=1; __jda=143920055.17142917184521525055223.1714291718.1714291718.1714299139.2; __jdb=143920055.1.17142917184521525055223|2.1714299139; 3AB9D23F7A4B3CSS=jdd03LWP4N4353YBIRVSKYG5OJTAYE53IO7OBOIOY6BEAE6E2YG4TMEWOOMASARQ5YNZHWNQPFOFWFUDMRNSEZYP2P6JN7UAAAAMPEQYPFNAAAAAACA2MAXT4FUXFUYX; qrsc=3; 3AB9D23F7A4B3C9B=LWP4N4353YBIRVSKYG5OJTAYE53IO7OBOIOY6BEAE6E2YG4TMEWOOMASARQ5YNZHWNQPFOFWFUDMRNSEZYP2P6JN7U";

	@PostConstruct
	private void init()
	{
		if(cartDb.findById(only_cart_id).isEmpty())
		{
			Cart cart=new Cart();
			cart.setId(only_cart_id);
			cartDb.save(cart);
		}
	}

	@Override
	@Cacheable(value = "productsCache")
	public List<Product> getProducts() throws IOException {
		try {
			if (products == null) {
				products = parseJD("Java");
				productDb.saveAll(products);
			}
		} catch (IOException e) {
			products = new ArrayList<>();
		}
		return products;
	}
	@Override
	@Cacheable(value = "productsCache")
	public Product getProduct(String productId) throws IOException {
		for (Product p : getProducts()) {
			if (p.getId().equals(productId)) {
				return p;
			}
		}
		return null;
	}
	public static List<Product> parseJD(String keyword) throws IOException {
		//获取请求https://search.jd.com/Search?keyword=java
		String url = "https://search.jd.com/Search?keyword=" + keyword;
		//解析网页
		Map<String, String> cookies = Arrays.stream(cookieString.split(";"))
			.map(cookie -> cookie.split("=", 2))
			.collect(Collectors.toMap(
				parts -> parts[0].trim(),
				parts -> parts.length > 1 ? parts[1].trim() : "",
				(oldValue, newValue) -> newValue // 当遇到重复的键时，使用新值覆盖旧值
			));

		Document document = Jsoup.connect(url)
			.cookies(cookies) // 设置所有的 cookie
			.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
			.timeout(10000)
			.get();
		//保存document到本地
		// 创建 FileWriter
		FileWriter fileWriter = new FileWriter("output.html");
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		// 将 Document 写入文件
		bufferedWriter.write(document.html());
		// 关闭 BufferedWriter
		bufferedWriter.close();
		Element goodslist = document.getElementById("J_goodsList");
		//所有js的方法都能用
		//Element element = document.select("li");
		//获取所有li标签
		Elements elements = goodslist.getElementsByTag("li");
		List<Product> list = new ArrayList<>();

		//获取元素的内容
		for (Element el : elements
		) {
			//关于图片特别多的网站，所有图片都是延迟加载的
			String id = el.attr("data-spu");
			//what the fuck
			try {
				Long.parseLong(id);
			}catch(NumberFormatException e){
				continue;
			}
			String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
			//String price = el.getElementsByAttribute("data-price").text();

			String price = el.getElementsByAttribute("data-price").getFirst().text();
			try
			{
				Double.parseDouble(price);
			} catch (NumberFormatException e) {
				continue;
			}
			String title = el.getElementsByClass("p-name").eq(0).text();
			if (title.indexOf("，") >= 0)
				title = title.substring(0, title.indexOf("，"));

			Product product = new Product(id, title, Double.parseDouble(price), img);

			list.add(product);
		}
		return list;
	}
	@Override
	public Cart getCart() {
		return cartDb.findById(only_cart_id).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setId(only_cart_id);
			cartDb.save(newCart);
			return newCart;
		});
	}

	@Override
	public Product getProduct(Long productId)
	{
		System.out.println("getProduct: " + productId);
		return productDb.findById(productId).orElse(null);
	}

	@Override
	public Cart saveCart(Cart cart)
	{
		if(cart.getId() == null)
		{
			cart.setId(only_cart_id);
		}
		return cartDb.save(cart);
	}
}

