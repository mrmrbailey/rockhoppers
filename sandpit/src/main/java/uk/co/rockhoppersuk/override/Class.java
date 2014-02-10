package uk.co.rockhoppersuk.override;

public class Class extends BaseClass {
	
	@Override
	public void service(int i) {
		super.service(i);
		System.out.println("class :" + i);
	}

}
