
package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class CreateManagerForm {

	private String	username;
	private String	password;
	private String	confirmPassword;
	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	company;
	private Integer	vat;
	private String	holderName;
	private String	brandName;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private Integer	cvv;
	private Boolean	isAgree;


	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}
	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}
	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@NotBlank
	public String getNumber() {
		return this.number;
	}
	public void setNumber(final String number) {
		this.number = number;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}
	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	@Min(2017)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}
	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCvv() {
		return this.cvv;
	}
	public void setCvv(final Integer cvv) {
		this.cvv = cvv;
	}

	@NotBlank
	public String getCompany() {
		return this.company;
	}

	public void setCompany(final String company) {
		this.company = company;
	}

	@NotNull
	public Integer getVat() {
		return this.vat;
	}

	public void setVat(final Integer vat) {
		this.vat = vat;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "(\\+\\d{1,3} )?(\\(\\d{1,3}\\) )?(\\w{4,})")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public Boolean getIsAgree() {
		return this.isAgree;
	}

	public void setIsAgree(final Boolean isAgree) {
		this.isAgree = isAgree;
	}

}
