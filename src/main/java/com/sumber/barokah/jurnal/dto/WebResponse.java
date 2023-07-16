package com.sumber.barokah.jurnal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebResponse<T> {

    // class ini dibuat untuk response body, Sebagai wrapper Type child
    // karena response berubah2 kita buat sama menjadi Generic Class
    // class ini dibuat untuk response body, Success dan Error.
    // ketika client consume/hit endpoint akan di beri response seperti format di bawah

    /**
     * bentuk response body :
     * Success
     *    {
     *      data: {
     *              // data
     *            }
     *     }
     *
     * Error
     *     {
     *         errors : pesan error
     *     }
     */

    private T data;

    private Integer status_code;

    private HttpStatus status;

    private String message;

    private String errors;

    private PagingResponse paging;

}
