package portfolio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portfolio.dto.ContactDto;
import portfolio.entities.ContactEntity;
import portfolio.repository.ContactRepository;

@Service
public class ContactServiceImp implements ContactService {

    @Autowired
    private ContactRepository contactRepositry;

    @Override
    public ContactEntity savecontact(ContactDto contactDto) {

        ContactEntity contactEntitey = new ContactEntity();

        // FIXED: setters now working after Lombok removal
        contactEntitey.setName(contactDto.getName());
        contactEntitey.setEmail(contactDto.getEmail());
        contactEntitey.setSubject(contactDto.getSubject());
        contactEntitey.setMessage(contactDto.getMessage());

        return contactRepositry.save(contactEntitey);
    }

	@Override
	public boolean isContactEmailExist(String email) {
		// TODO Auto-generated method stub
		return contactRepositry.existsByEmail(email);
	}

	@Override
	public List<ContactEntity> readAllContacts() {
		
		return contactRepositry.findAll();
	}

	@Override
	public void deleteContactById(int id) {
		// TODO Auto-generated method stub
		contactRepositry.deleteById(id);
		
	}
}
