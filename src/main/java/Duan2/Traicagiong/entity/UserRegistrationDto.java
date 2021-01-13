package Duan2.Traicagiong.entity;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserRegistrationDto {
	
    @NotEmpty(message = "Vui Lòng nhập Tên")
    private String name;

    @NotEmpty(message = "Vui Lòng nhập Số Điện Thoại")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Vui Lòng nhập Đúng định dạng Số Điện Thoại")
    private String phone;

    @NotEmpty(message = "Vui Lòng nhập Mật khẩu")
    private String password;

    @NotEmpty(message = "Vui Lòng nhập Xác nhận mật khẩu")
    private String confirmPassword;

    @Email
    @NotEmpty(message = "Vui Lòng nhập Email")
    private String email;
    
    @NotEmpty(message = "Vui Lòng nhập địa chỉ")
    private String address;

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

}