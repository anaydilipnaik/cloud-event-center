export function canUserViewParticipantForum(userId, eventDetails) {
    if (eventDetails.organizer && eventDetails.organizer.id ) {
      if (eventDetails.organizer.id === userId) {
        console.log("canUserViewParticipantForum:: User created event:: returning true");
        return true
      }
    };

    const { participants } = eventDetails;
    if (Array.isArray(participants)) {
      for(let i = 0; i < participants.length; i++) {
        const { participant, status } = participants[i];
          if (status === "Approved") {
            if (participant && participant.id === userId) {
              console.log("canUserViewParticipantForum:: approved participant:: returning true");
              return true;
            }
        }
        };
    };
};