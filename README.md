# CLI Convert JSON to Smile

The author does not regularly use Java or Maven, so any improvements
are welcome. Build this with:

    mvn clean compile assembly:single

Then try it out with:

    echo '{"foo":5,"bar":false}' | java -jar ./target/smilecli-1.0-jar-with-dependencies.jar | xxd

The application expects JSON on standard in and outputs SMILE-encoded
JSON. In the example above, the output is piped through `xxd` to avoid
spewing garbage on the terminal.

SMILE's backreference features for compression are disabled in the
application. It's easy to change this in the source code if it
is needed.
