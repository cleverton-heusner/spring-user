package cleverton.heusner.port.output.provider.address;

import cleverton.heusner.domain.model.Address;

public interface AddressProvider {

    Address getAddressByZipCode(final Address address);
}
