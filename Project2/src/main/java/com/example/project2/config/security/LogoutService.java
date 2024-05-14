package com.example.project2.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import com.example.project2.repository.TokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenRepository tokenRepository;
  // Nếu API là logout thì sẽ chạy hàm logout
  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    final String authHeader = request.getHeader("Authorization"); // Lấy ra header từ request
    final String jwt;
    // Kiểm tra xem header có null hoặc bắt đầu bằng chuỗi Bearer không
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    // Nếu header tồn tại và đúng định dạng sẽ bỏ qua 7 ký tự đầu tiên
    jwt = authHeader.substring(7); // Cắt chuỗi header từ ký tự index số 7 trở đi ( "Bearer " )
    var storedToken = tokenRepository.findByToken(jwt) // Tìm token trong database theo chuỗi jwt
        .orElse(null);
    if (storedToken != null) {  // Nếu token tồn tại trong database
      storedToken.setExpired(true);  // Thay đổi thời gian hết hạn = true
      storedToken.setRevoked(true);  // Thay đổi trạng thái thu hồi = true
      tokenRepository.save(storedToken); // Lưu lại token sau khi đã thay đổi thời gian và trạng thái
      SecurityContextHolder.clearContext();  // Xóa thông tin phiên đăng nhập trước đó có trong ContextHolder
    }
  }
}
