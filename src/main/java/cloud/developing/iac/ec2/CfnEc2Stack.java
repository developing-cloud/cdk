package cloud.developing.iac.ec2;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.Tag;
import software.amazon.awscdk.services.ec2.CfnInstance;

class CfnEc2Stack extends Stack {

	CfnEc2Stack(Construct scope, String id, String ami, String instanceType, int ec2Count) {
		super(scope, id);
		for (int i = 1; i <= ec2Count; i++) {
			var ec2Id = id + "-" + i;
			var ec2 = CfnInstance.Builder.create(this, ec2Id).imageId(ami).instanceType(instanceType).build();
			Tag.add(ec2, "Name", ec2Id);
		}
	}

}
