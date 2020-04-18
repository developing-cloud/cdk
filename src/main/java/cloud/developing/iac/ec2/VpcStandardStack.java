package cloud.developing.iac.ec2;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.Vpc;

class VpcStandardStack extends Stack {

	final Vpc vpc;

	VpcStandardStack(Construct scope, String id) {
		super(scope, id);
		this.vpc = new Vpc(this, "vpc");
	}

}
