package com.appleframework.auto.calculate.fence.consumer;

import javax.annotation.Resource;

import com.appleframework.auto.bean.fence.SyncOperate;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.jms.jedis.consumer.TopicObjectMessageConsumer;

public class FenceConsumer extends TopicObjectMessageConsumer<Object> {

	@Resource
	private FenceInfoService fenceInfoService;

	@Override
	public void processMessage(Object message) {
		if (message instanceof SyncOperate) {
			SyncOperate operate = (SyncOperate) message;
			if (operate.getOperateType() == SyncOperate.CREATE) {
				fenceInfoService.create(operate.getNewFence());
			} else if (operate.getOperateType() == SyncOperate.UPDATE) {
				fenceInfoService.update(operate.getOldFence(), operate.getNewFence());
			} else {
				fenceInfoService.delete(operate.getOldFence());
			}
		}
	}

}
