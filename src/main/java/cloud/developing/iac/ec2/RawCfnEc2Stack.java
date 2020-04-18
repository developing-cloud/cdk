package cloud.developing.iac.ec2;

import static cloud.developing.iac.common.CdkMap.map;

import software.amazon.awscdk.core.CfnResource;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.Tag;

class RawCfnEc2Stack extends Stack {

	RawCfnEc2Stack(Construct scope, String id, String ami, String instanceType, int ec2Count) {
		super(scope, id);
		for (int i = 1; i <= ec2Count; i++) {
			CfnResource.Builder.create(this, id + "-" + i).type("AWS::EC2::Instance")
			        .properties(map().with("ImageId", ami).with("InstanceType", instanceType)).build();
		}
		Tag.add(this, "Name", id);
	}

}
