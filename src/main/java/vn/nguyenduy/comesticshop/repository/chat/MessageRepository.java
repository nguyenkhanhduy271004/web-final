// package vn.nguyenduy.laptopshop.repository.chat;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

// public interface MessageRepository extends JpaRepository<Message, Long> {

// @Query("SELECT m FROM Message m WHERE (m.messageFrom = :from AND m.messageTo
// = :to) OR (m.messageFrom = :to AND m.messageTo = :from) ORDER BY
// m.createdDatetime ASC")
// List<Message> findMessagesBetween(@Param("from") Integer from, @Param("to")
// Integer to);
// }
