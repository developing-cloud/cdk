package cloud.developing.iac.ec2;

import software.amazon.awscdk.core.IAspect;
import software.amazon.awscdk.core.IConstruct;
import software.amazon.awscdk.services.ec2.CfnRouteTable;
import software.amazon.awscdk.services.ec2.CfnSubnet;

class SubnetTagAspect implements IAspect {

	private String key, value, suffix;

	SubnetTagAspect(String key, String value, String suffix) {
		this.key = key;
		this.value = value;
		this.suffix = suffix;
	}

	@Override
	public void visit(IConstruct node) {

		if (node instanceof CfnSubnet) {
			((CfnSubnet) node).getTags().setTag(key, value("subnet"), 100);
		} else if (node instanceof CfnRouteTable) {
			((CfnRouteTable) node).getTags().setTag(key, value("rt"), 100);
		}
	}

	private String value(String networkComponent) {
		return value + "-" + networkComponent + "-" + suffix;
	}

}
