//package com.chengshi.shop.controller.mobile.order;
//
//import com.chengshi.shop.model.order.Order;
//import com.chengshi.shop.model.order.OrderPayment;
//import com.chengshi.shop.service.order.OrderPaymentService;
//import com.chengshi.shop.service.order.OrderService;
//import com.chengshi.shop.service.system.ShopConfigService;
//import com.chengshi.shop.util.EnumUtil;
//import com.chengshi.shop.util.MessageUtils;
//import com.chengshi.shop.util.SessionUtils;
//import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
//import com.github.binarywang.wxpay.bean.request.*;
//import com.github.binarywang.wxpay.bean.result.*;
//import com.github.binarywang.wxpay.exception.WxPayException;
//import com.github.binarywang.wxpay.service.WxPayService;
//import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.math.BigDecimal;
//import java.net.InetAddress;
//import java.util.Date;
//import java.util.HashMap;
//
///**
// * 订单支付模块
// *
// * @author xuxinlong
// * @version 2017年07月24日
// */
//@RestController
//@RequestMapping(value = "mobile/pay")
//public class MobileOrderPaymentController {
//    @Resource
//    private OrderPaymentService orderPaymentService;
//    @Resource
//    private WxPayService wxService;
//    @Resource
//    private OrderService orderService;
//    @Resource
//    private ShopConfigService shopConfigService;
//
//    @Value("${wechat.mp.host}")
//    private String HOST;
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    /**
//     * <pre>
//     * 查询订单(详见https://com.github.binarywang.wechat.pay.bean.pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2)
//     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
//     * 需要调用查询接口的情况：
//     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
//     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
//     * ◆ 调用被扫支付API，返回USERPAYING的状态；
//     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
//     * 接口地址：https://api.mch.weixin.qq.com/pay/orderquery
//     * </pre>
//     *
//     * @param transactionId 微信订单号
//     * @param outTradeNo    商户系统内部的订单号，当没提供transactionId时需要传这个。
//     */
//    @GetMapping("/queryOrder")
//    public WxPayOrderQueryResult queryOrder(@RequestParam(required = false) String transactionId,
//                                            @RequestParam(required = false) String outTradeNo)
//            throws WxPayException {
//        return this.wxService.queryOrder(transactionId, outTradeNo);
//    }
//
//    /**
//     * <pre>
//     * 关闭订单
//     * 应用场景
//     * 以下情况需要调用关单接口：
//     * 1. 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
//     * 2. 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
//     * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
//     * 接口地址：https://api.mch.weixin.qq.com/pay/closeorder
//     * 是否需要证书：   不需要。
//     * </pre>
//     *
//     * @param outTradeNo 商户系统内部的订单号
//     */
//    @GetMapping("/closeOrder/{outTradeNo}")
//    public WxPayOrderCloseResult closeOrder(@PathVariable String outTradeNo) {
//        try {
//            return this.wxService.closeOrder(outTradeNo);
//        } catch (WxPayException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    /**
//     * 该接口调用“统一下单”接口，并拼装发起支付请求需要的参数
//     * 详见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
//     *
//     * @param orderId
//     * @return
//     * @throws WxPayException
//     */
//    @PostMapping("/wxPayInfo")
//    public HashMap<String, Object> getPayInfo(@RequestParam Integer orderId) {
//        HashMap<String, Object> retMap = MessageUtils.success();
//        try {
//            Order order = orderService.getOrderByOrderId(orderId);
//            if (order.getStatus() != EnumUtil.ORDER_STATUS.待付款.getValue().byteValue()) {
//                retMap = MessageUtils.error("订单已支付");
//                return retMap;
//            }
//            BigDecimal finalMoney = order.getTotalAmount().add(order.getFare()).subtract(order.getPayed());
//            WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
//            request.setBody(shopConfigService.getConfig("SHOP_NAME") + "-订单号" + order.getOrderNum());
//            request.setNotifyURL(HOST + "/mobile/pay/getWxOrderNotify.do");
//            request.setDeviceInfo("WEB");
//            request.setOutTradeNo(order.getOrderNum());
//            request.setTotalFee(WxPayBaseRequest.yuanToFee(finalMoney.toString()));
//            request.setTradeType("JSAPI");
//            request.setSpbillCreateIp(InetAddress.getLocalHost().getHostAddress());
//            request.setOpenid(SessionUtils.getSession().getAttribute("wxPayOpenId").toString());
//            retMap.put("payInfo", this.wxService.getPayInfo(request));
//        } catch (Exception e) {
//            log.error("微信支付失败！订单orderId：{},原因:{}", orderId, e.getMessage());
//            retMap = MessageUtils.error("支付失败，请稍后重试！");
//        }
//        return retMap;
//    }
//
//    /**
//     * <pre>
//     * 微信支付-申请退款
//     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
//     * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
//     * </pre>
//     *
//     * @param request 请求对象
//     * @return 退款操作结果
//     */
//    @PostMapping("/refund")
//    public WxPayRefundResult refund(@RequestBody WxPayRefundRequest request) throws WxPayException {
//        return this.wxService.refund(request);
//    }
//
//    /**
//     * <pre>
//     * 微信支付-查询退款
//     * 应用场景：
//     *  提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，
//     *  银行卡支付的退款3个工作日后重新查询退款状态。
//     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
//     * 接口链接：https://api.mch.weixin.qq.com/pay/refundquery
//     * </pre>
//     * 以下四个参数四选一
//     *
//     * @param transactionId 微信订单号
//     * @param outTradeNo    商户订单号
//     * @param outRefundNo   商户退款单号
//     * @param refundId      微信退款单号
//     * @return 退款信息
//     */
//    @GetMapping("/refundQuery")
//    public WxPayRefundQueryResult refundQuery(@RequestParam(required = false) String transactionId,
//                                              @RequestParam(required = false) String outTradeNo,
//                                              @RequestParam(required = false) String outRefundNo,
//                                              @RequestParam(required = false) String refundId)
//            throws WxPayException {
//        return this.wxService.refundQuery(transactionId, outTradeNo, outRefundNo, refundId);
//    }
//
//    /**
//     * 微信支付回调详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
//     *
//     * @param request
//     * @return
//     * @throws WxPayException
//     */
//    @PostMapping("/getWxOrderNotify")
//    public String getWxOrderNotify(HttpServletRequest request) throws WxPayException {
//        try {
//            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
//            WxPayOrderNotifyResult result = wxService.getOrderNotifyResult(xmlResult);
//            // 验证结果正确
//            String orderNum = result.getOutTradeNo();
//            String totalFee = WxPayBaseResult.feeToYuan(result.getTotalFee());
//            Order order = orderService.getOrderByOrderNum(orderNum);
//            if (order.getStatus().intValue() == EnumUtil.ORDER_STATUS.待付款.getValue()) {
//                order.setPayTime(new Date());
//                // 添加支付明细表中，实际支付的部分
//                OrderPayment orderPayment = new OrderPayment();
//                orderPayment.setOrderId(order.getOrderId());
//                orderPayment.setMoney(new BigDecimal(totalFee));
//                orderPayment.setPaymentMethod(EnumUtil.PAYMENT_METHOD.微信.getValue().byteValue());
//                orderPayment.setContent("微信支付（订单：" + order.getOrderNum() + "）");
//                orderPaymentService.saveOrderPayment(orderPayment, order);
//            }
//            //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
//            return WxPayOrderNotifyResponse.success("处理成功!");
//        } catch (Exception e) {
//            log.error("微信回调结果异常,异常原因{}", e.getMessage());
//            return WxPayOrderNotifyResponse.fail(e.getMessage());
//        }
//
//    }
//
//
//    /**
//     * <pre>
//     * 扫码支付模式一生成二维码的方法
//     * 二维码中的内容为链接，形式为：
//     * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
//     * 其中XXXXX为商户需要填写的内容，商户将该链接生成二维码，如需要打印发布二维码，需要采用此格式。商户可调用第三方库生成二维码图片。
//     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
//     * </pre>
//     *
//     * @param productId  产品Id
//     * @param logoFile   商户logo图片的文件对象，可以为空
//     * @param sideLength 要生成的二维码的边长，如果为空，则取默认值400
//     * @return 生成的二维码的字节数组
//     */
//    public byte[] createScanPayQrcodeMode1(String productId, File logoFile, Integer sideLength) {
//        return this.wxService.createScanPayQrcodeMode1(productId, logoFile, sideLength);
//    }
//
//    /**
//     * <pre>
//     * 扫码支付模式一生成二维码的方法
//     * 二维码中的内容为链接，形式为：
//     * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
//     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
//     * </pre>
//     *
//     * @param productId 产品Id
//     * @return 生成的二维码URL连接base64码
//     */
//    @GetMapping(value = "/scan")
//    public String createScanPayQrcodeMode1(@RequestParam String productId) throws Exception {
//        return MatrixToImageWriterUtil.createMatrix(this.wxService.createScanPayQrcodeMode1(productId));
//    }
//
//    /**
//     * <pre>
//     * 扫码支付模式二生成二维码的方法
//     * 对应链接格式：weixin：//wxpay/bizpayurl?sr=XXXXX。请商户调用第三方库将code_url生成二维码图片。
//     * 该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
//     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5
//     * </pre>
//     *
//     * @param codeUrl    微信返回的交易会话的二维码链接
//     * @param logoFile   商户logo图片的文件对象，可以为空
//     * @param sideLength 要生成的二维码的边长，如果为空，则取默认值400
//     * @return 生成的二维码的字节数组
//     */
//    public byte[] createScanPayQrcodeMode2(String codeUrl, File logoFile, Integer sideLength) {
//        return this.wxService.createScanPayQrcodeMode2(codeUrl, logoFile, sideLength);
//    }
//
//    /**
//     * <pre>
//     * 交易保障
//     * 应用场景：
//     *  商户在调用微信支付提供的相关接口时，会得到微信支付返回的相关信息以及获得整个接口的响应时间。
//     *  为提高整体的服务水平，协助商户一起提高服务质量，微信支付提供了相关接口调用耗时和返回信息的主动上报接口，
//     *  微信支付可以根据商户侧上报的数据进一步优化网络部署，完善服务监控，和商户更好的协作为用户提供更好的业务体验。
//     * 接口地址： https://api.mch.weixin.qq.com/payitil/report
//     * 是否需要证书：不需要
//     * </pre>
//     *
//     * @param request
//     */
//    @PostMapping("/report")
//    public void report(@RequestBody WxPayReportRequest request) throws WxPayException {
//        this.wxService.report(request);
//    }
//
//    /**
//     * <pre>
//     * 下载对账单
//     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
//     * 注意：
//     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
//     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
//     * 3、对账单中涉及金额的字段单位为“元”。
//     * 4、对账单接口只能下载三个月以内的账单。
//     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadbill
//     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单</a>
//     * </pre>
//     *
//     * @param billDate   对账单日期 bill_date	下载对账单的日期，格式：20140603
//     * @param billType   账单类型	bill_type	ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
//     * @param tarType    压缩账单	tar_type	非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
//     * @param deviceInfo 设备号	device_info	非必传参数，终端设备号
//     * @return 保存到本地的临时文件
//     */
//    @GetMapping("/downloadBill")
//    public WxPayBillResult downloadBill(@RequestParam String billDate,
//                                        @RequestParam String billType,
//                                        @RequestParam String tarType,
//                                        @RequestParam String deviceInfo) throws WxPayException {
//        return this.wxService.downloadBill(billDate, billType, tarType, deviceInfo);
//    }
//
//    /**
//     * <pre>
//     * 撤销订单API
//     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3
//     * 应用场景：
//     *  支付交易返回失败或支付系统超时，调用该接口撤销交易。如果此订单用户支付失败，微信支付系统会将此订单关闭；如果用户支付成功，微信支付系统会将此订单资金退还给用户。
//     *  注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
//     *  调用支付接口后请勿立即调用撤销订单API，建议支付后至少15s后再调用撤销订单接口。
//     *  接口链接 ：https://api.mch.weixin.qq.com/secapi/pay/reverse
//     *  是否需要证书：请求需要双向证书。
//     * </pre>
//     */
//    @PostMapping("/reverseOrder")
//    public WxPayOrderReverseResult reverseOrder(@RequestBody WxPayOrderReverseRequest request) throws WxPayException {
//        return this.wxService.reverseOrder(request);
//    }
//
//}
