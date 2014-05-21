package _0.namecard.vo;

public class NameCardVO{
	private int idx;
	private String name;
	private String address;
	private String tel;
	private String company;
	private String email;
	private int group;
	
	public NameCardVO() {

	}

	public NameCardVO(int idx, String name, String address, String tel,
			String company, String email, int group) {
		super();
		this.idx = idx;
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.company = company;
		this.email = email;
		this.group = group;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "NameCardVO [idx=" + idx + ", name=" + name + ", address="
				+ address + ", tel=" + tel + ", company=" + company
				+ ", email=" + email + ", group=" + group + "]";
	}

	
}
