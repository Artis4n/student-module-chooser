package model;

import java.io.Serializable;
import java.util.Objects;

public class Module implements Comparable<Module>, Serializable {


	private static final long serialVersionUID = -7120769434678280011L;
	private String moduleCode;
	private String moduleName;
	private int credits;
	private boolean mandatory;

	public Module(String moduleCode, String moduleName) {
		this(moduleCode, moduleName, 30, false);
	}

	public Module(String moduleCode, String moduleName, int credits, boolean mandatory) {
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.credits = credits;
		this.mandatory = mandatory;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}


	@Override
	public String toString() {
		//a non-standard toString that simply returns the course name,
		//so as to assist in displaying modules correctly in a ListView<Module> in the view
		//-Note- you may customise this if you wish to do so.
		return moduleCode + " : " + moduleName;
	}

	public String actualToString() {
		return "Module:[moduleCode=" + moduleCode + ", moduleName="
				+ moduleName + ", credits=" + credits + ", mandatory=" + mandatory + "]";
	}


	@Override
	public int compareTo(Module other) {
		int result = Boolean.compare(other.mandatory, this.mandatory);

		if (result == 0) {
			result = Integer.compare(this.credits, other.credits);

			if (result == 0) {
				result = this.moduleCode.compareTo(other.moduleCode);

				if (result == 0) {
					result = this.moduleName.compareTo(other.moduleName);
				}

			}
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Module other = (Module) obj;

		return this.mandatory == other.mandatory && this.credits == other.credits &&
				this.moduleCode.equals(other.moduleCode) && this.moduleName.equals(other.moduleName);

	}

	@Override
	public int hashCode() {
		return Objects.hash(mandatory, credits, moduleCode, moduleName);
	}


}
