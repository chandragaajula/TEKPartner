package net.tekpartner.code.sample.drools;

public class ApplicantForm {

	private Integer applicantId;
	private boolean eligible = true; // eligible by default

	public Integer getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Integer applicantId) {
		this.applicantId = applicantId;
	}

	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

	@Override
	public String toString() {
		return "applicantId=" + applicantId + ", eligible=" + eligible;
	}
}