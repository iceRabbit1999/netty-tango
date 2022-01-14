package com.icerabbit.nettytango.example.decoder.delitmiter.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName
 * @Description TODO
 * @Author iceRabbit
 * @Date 2022/1/13 14:38
 **/
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "INVITE sip:+8613460308414@ha.ims.mnc000.mcc460.3gppnetwork.org SIP/2.0\\r\\nFrom: <sip:+8613802881946@192.168.2.19:5060;user=phone>;tag=114-202-581236\\r\\nTo: <sip:+8613460308414@kafscscf4bzx.ha.chinamobile.com:5082;user=phone>\\r\\nContact: <sip:10.193.106.51:5060>;+g.3gpp.icsi-ref=\\\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\\\";audio;+sip.instance=\\\"<urn:gsma:imei:35305718-180639-0>\\\"\\r\\nCall-ID: g-114-8-522-1286875406@10.193.106.51\\r\\nCSeq: 581237 INVITE\\r\\nContent-Type: application/sdp\\r\\nMin-SE: 90\\r\\nP-Asserted-Identity: <sip:+8613802881946@gd.ims.mnc000.mcc460.3gppnetwork.org>,<tel:+8613802881946>\\r\\nSupported: timer,100rel,path,precondition,replaces\\r\\nP-Access-Network-Info: 3GPP-E-UTRAN; utran-cell-id-3gpp=4600028552A92F0B;sbc-domain=sbc02.020.000.gd.chinamobile.com;ue-ip=[2409:815A:60F:4AB9:823:B140:4F43:6798];ue-port=53803;network-provided\\r\\nP-Early-Media: supported\\r\\nP-Visited-Network-ID: \\\"gzpsbc2bhw.gd.ims.mnc000.mcc460.3gppnetwork.org\\\"\\r\\nAllow: INVITE,UPDATE,BYE,PRACK,INFO,OPTIONS,CANCEL,SUBSCRIBE,ACK,REFER,NOTIFY,REGISTER,PUBLISH,MESSAGE\\r\\nMax-Forwards: 65\\r\\nSession-id: 94c47c4b217ea664559aabd53ff32af6\\r\\nAccept-Contact: *;+g.3gpp.icsi-ref=\\\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\\\"\\r\\nX-ZTE-Cookie: 7zs4rm3;id=z9hG4bK1o9777p898m8buks9ubw77pq4aqpqkwee@[2409:8086:8612:0611:0000:0000:0000:0074]\\r\\nVia: SIP/2.0/UDP 10.190.96.33:5077;branch=z9hG4bK*3-11-16648-1124-12-933-0*L2wPB0c1a11egbffdbeb.3,SIP/2.0/UDP 10.193.106.51:5060;received=10.193.106.51;branch=z9hG4bK201-533040-405248005100799864\\r\\nRoute: <sip:hhgzhas1aeb.gzh.ha.chinamobile.com:5060;lr>,<sip:zteodi0003000B0E4006B10710045A0210681E@kafscscf4bzx.ha.chinamobile.com:5077;lr>\\r\\nP-Charging-Vector: icid-value=\\\"gzpsbc2bhw.193.332.20220112014308\\\";orig-ioi=chinamobile\\r\\nSession-Expires: 1800\\r\\nRecord-Route: <sip:10.190.96.33:5077;lr;zte-did=3-11-16648-1124-12-933-0>\\r\\nContent-Length: 937\\r\\n\\r\\nv=0\\r\\no=- 1641951790 1641951790 IN IP4 10.186.3.78\\r\\ns=SBC call\\r\\nc=IN IP4 10.188.28.229\\r\\nt=0 0\\r\\nm=audio 44040 RTP/AVP 109 104 110 102 108 105 100 18 8 0\\r\\nb=AS:80\\r\\nb=RS:625\\r\\nb=RR:1875\\r\\na=rtpmap:109 EVS/16000\\r\\na=fmtp:109 br=24.4; bw=wb; max-red=220; ch-aw-recv=-1\\r\\na=rtpmap:104 AMR-WB/16000\\r\\na=fmtp:104 max-red=220;mode-change-capability=2\\r\\na=rtpmap:110 AMR-WB/16000\\r\\na=fmtp:110 octet-align=1;max-red=220;mode-change-capability=2\\r\\na=rtpmap:102 AMR/8000\\r\\na=fmtp:102 max-red=220;mode-change-capability=2\\r\\na=rtpmap:108 AMR/8000\\r\\na=fmtp:108 octet-align=1;max-red=220;mode-change-capability=2\\r\\na=rtpmap:105 telephone-event/16000\\r\\na=fmtp:105 0-15\\r\\na=rtpmap:100 telephone-event/8000\\r\\na=fmtp:100 0-15\\r\\na=ptime:20\\r\\na=maxptime:240\\r\\na=sendrecv\\r\\na=des:qos mandatory local sendrecv\\r\\na=curr:qos local none\\r\\na=des:qos optional remote sendrecv\\r\\na=curr:qos remote none\\r\\na=rtpmap:18 G729/8000\\r\\na=fmtp:18 annexb=no\\r\\na=rtpmap:8 PCMA/8000\\r\\na=rtpmap:0 PCMU/8000\\r\\n";
        ctx.writeAndFlush(msg);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
