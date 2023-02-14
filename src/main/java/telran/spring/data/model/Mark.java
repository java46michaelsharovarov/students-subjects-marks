package telran.spring.data.model;

import java.util.Objects;

public class Mark {

	public long studId;
	public long subjId;
	public int mark;
	
	public Mark() {
	}

	public Mark(long stid, long suid, int mark) {
		this.studId = stid;
		this.subjId = suid;
		this.mark = mark;
	}

	@Override
	public int hashCode() {
		return Objects.hash(mark, studId, subjId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mark other = (Mark) obj;
		return mark == other.mark && studId == other.studId && subjId == other.subjId;
	}	
	
}
