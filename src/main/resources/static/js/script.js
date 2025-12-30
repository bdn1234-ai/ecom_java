/* Cập nhật từ file gốc: bdn1234-ai/ecom_java/.../static/js/script.js */
$(function(){
    // --- Logic hoa đào rơi (Thêm mới) ---
    const container = document.querySelector('.blossom-container');
    if (container) {
        function createPetal() {
            const petal = document.createElement('div');
            petal.classList.add('petal');

            const size = Math.random() * 15 + 10 + 'px';
            petal.style.width = size;
            petal.style.height = size;
            petal.style.left = Math.random() * 100 + 'vw';

            const duration = Math.random() * 6 + 4 + 's';
            petal.style.animationDuration = duration;

            container.appendChild(petal);

            setTimeout(() => {
                petal.remove();
            }, parseFloat(duration) * 1000);
        }
        setInterval(createPetal, 400); // Tạo hoa mỗi 0.4 giây
    }

    // --- Giữ nguyên các User Register validation gốc ---
    var $userRegister=$("#userRegister");
    if($userRegister.length) {
        $userRegister.validate({
            rules:{
                name:{ required:true, lettersonly:true },
                email: { required: true, space: true, email: true },
                mobileNumber: { required: true, space: true, numericOnly: true, minlength: 10, maxlength: 12 },
                password: { required: true, space: true },
                confirmpassword: { required: true, space: true, equalTo: '#pass' },
                address: { required: true, all: true },
                city: { required: true, space: true },
                state: { required: true },
                pincode: { required: true, space: true, numericOnly: true },
                img: { required: true }
            },
            messages:{
                name:{ required:'Bạn chưa nhập tên', lettersonly:'Tên không hợp lệ' },
                email: { required: 'Vui lòng nhập email', space: 'Không chứa khoảng trắng', email: 'Email không hợp lệ' },
                // ... (Các thông báo khác có thể việt hóa tương tự)
            }
        });
    }

    // --- Giữ nguyên Orders Validation gốc ---
    var $orders=$("#orders");
    if($orders.length) {
        $orders.validate({
            rules:{
                firstName:{ required:true, lettersonly:true },
                lastName:{ required:true, lettersonly:true },
                email: { required: true, space: true, email: true },
                mobileNo: { required: true, space: true, numericOnly: true, minlength: 10, maxlength: 12 },
                address: { required: true, all: true },
                city: { required: true, all: true },
                state: { required: true, all: true },
                pincode: { required: true, space: true, numericOnly: true },
                paymentType:{ required: true }
            }
        });
    }

    // --- Giữ nguyên Reset Password Validation gốc ---
    var $resetPassword=$("#resetPassword");
    if($resetPassword.length) {
        $resetPassword.validate({
            rules:{
                password: { required: true, space: true },
                confirmPassword: { required: true, space: true, equalTo: '#pass' }
            }
        });
    }
});

// Các phương thức bổ sung cho jQuery Validator
jQuery.validator.addMethod('lettersonly', function(value, element) {
    return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
});

jQuery.validator.addMethod('space', function(value, element) {
    return /^[^-\s]+$/.test(value);
});

jQuery.validator.addMethod('all', function(value, element) {
    return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
});

jQuery.validator.addMethod('numericOnly', function(value, element) {
    return /^[0-9]+$/.test(value);
});