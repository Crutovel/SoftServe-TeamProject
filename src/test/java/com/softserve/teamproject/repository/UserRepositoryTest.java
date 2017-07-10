package java.com.softserve.teamproject.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


/*
public class UserRepositoryTest {

  @Mock
  UserRepository userRep;
  @Mock
  User user;

  @Before
  public void setupMock() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void userRepFindByNickNameTest(){
    //Arrange
    when(userRep.findOne(2)).thenReturn(user);
    //Act
    User retrievedUser = userRep.getUserByNickName("nickName");
    //Assert
    assertThat(retrievedUser, null);
  }
}
*/