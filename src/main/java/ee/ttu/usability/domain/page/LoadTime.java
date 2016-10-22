package ee.ttu.usability.domain.page;

import jevg.ee.ttu.dataproperty.Unit;
import lombok.Data;

@Data
public class LoadTime {

	private Unit unit;
	
	private Integer contentLength;
	
}
