import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AutomatonResponse {

	Integer since;
	Integer to;

	String lexeme;

	String lastStatus;

	Boolean error;
	String errorDetail;

	public Integer getSince() {
		return since;
	}

	public void setSince(Integer since) {
		this.since = since;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public Boolean isError() {
		return error;
	}

	public void isError(Boolean error) {
		this.error = error;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public void set(int since, int to, String lexeme, String lastStatus, boolean error, String errorDetail){
		this.since = since;
		this.to = to;
		this.lexeme = lexeme;
		this.lastStatus = lastStatus;
		this.error = error;

		String detail = StringUtils.replace(errorDetail, "#", Integer.toString(since));
		detail = StringUtils.replace(detail, "$", Integer.toString(to));
		this.errorDetail = detail;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}