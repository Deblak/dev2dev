package co.simplon.dev2dev_business.dtos;

import co.simplon.dev2dev_business.dtos.validators.UniqueProviderUrl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record AutomatProviderUrl(
        @NotBlank @Length(max = 2300)  @UniqueProviderUrl @Pattern(regexp = "^https?:/{2}[^<>/\\s]+(?:/[^<>/\\s]+)*$",flags= Pattern.Flag.CASE_INSENSITIVE, message = "Url provided not valid. Please try again") String url

) {
}
