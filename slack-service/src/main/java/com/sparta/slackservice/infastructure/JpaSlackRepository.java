package com.sparta.slackservice.infastructure;
import com.sparta.slackservice.domain.model.Slack;
import com.sparta.slackservice.domain.repository.SlackRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaSlackRepository extends JpaRepository<Slack, UUID>, SlackRepository, SlackQueryDSLRepository {
}
