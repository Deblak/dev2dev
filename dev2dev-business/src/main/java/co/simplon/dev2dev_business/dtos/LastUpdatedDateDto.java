package co.simplon.dev2dev_business.dtos;

import co.simplon.dev2dev_business.dtos.validators.ValidFormatDate;

import java.time.OffsetDateTime;

public record LastUpdatedDateDto( @ValidFormatDate OffsetDateTime lastDateUpdatedRetrive) {
}
