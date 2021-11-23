package Login.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Login.web.Entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, String>{

}
