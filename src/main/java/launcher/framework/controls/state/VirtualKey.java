package launcher.framework.controls.state;

import net.java.games.input.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum VirtualKey {
    VK_ENTER('\n', Component.Identifier.Key.RETURN),
    VK_BACK_SPACE('\b', Component.Identifier.Key.BACK),
    VK_TAB('\t',Component.Identifier.Key.TAB),
    VK_CANCEL(0x03, Component.Identifier.Key.ESCAPE),
    VK_CLEAR(0x0C, Component.Identifier.Key.ESCAPE),
    VK_SHIFT(0x10, Component.Identifier.Key.LSHIFT),
    VK_CONTROL(0x11, Component.Identifier.Key.LCONTROL),
    VK_ALT(0x12, Component.Identifier.Key.LALT),
    VK_PAUSE(0x13, Component.Identifier.Key.PAUSE),
    VK_CAPS_LOCK(0x14, Component.Identifier.Key.CAPITAL),
    VK_ESCAPE(0x1B, Component.Identifier.Key.ESCAPE),
    VK_SPACE(0x20, Component.Identifier.Key.SPACE),
    VK_PAGE_UP(0x21, Component.Identifier.Key.PAGEUP),
    VK_PAGE_DOWN(0x22, Component.Identifier.Key.PAGEDOWN),
    VK_END(0x23, Component.Identifier.Key.END),
    VK_HOME(0x24, Component.Identifier.Key.HOME),
    VK_LEFT(0x25, Component.Identifier.Key.LEFT),
    VK_UP(0x26, Component.Identifier.Key.UP),
    VK_RIGHT(0x27, Component.Identifier.Key.RIGHT),
    VK_DOWN(0x28, Component.Identifier.Key.DOWN),
    VK_COMMA(0x2C, Component.Identifier.Key.COMMA),
    VK_MINUS(0x2D, Component.Identifier.Key.MINUS),
    VK_PERIOD(0x2E, Component.Identifier.Key.PERIOD),
    VK_SLASH(0x2F, Component.Identifier.Key.SLASH),
    VK_0(0x30, Component.Identifier.Key._0),
    VK_1(0x31, Component.Identifier.Key._1),
    VK_2(0x32, Component.Identifier.Key._2),
    VK_3(0x33, Component.Identifier.Key._3),
    VK_4(0x34, Component.Identifier.Key._4),
    VK_5(0x35, Component.Identifier.Key._5),
    VK_6(0x36, Component.Identifier.Key._6),
    VK_7(0x37, Component.Identifier.Key._7),
    VK_8(0x38, Component.Identifier.Key._8),
    VK_9(0x39, Component.Identifier.Key._9),
    VK_SEMICOLON(0x3B, Component.Identifier.Key.SEMICOLON),
    VK_EQUALS(0x3D, Component.Identifier.Key.EQUALS),
    VK_A(0x41, Component.Identifier.Key.A),
    VK_B(0x42, Component.Identifier.Key.B),
    VK_C(0x43, Component.Identifier.Key.C),
    VK_D(0x44, Component.Identifier.Key.D),
    
    VK_E(0x45, Component.Identifier.Key.E),
    
    VK_F(0x46, Component.Identifier.Key.F),
    
    VK_G(0x47, Component.Identifier.Key.G),
    
    VK_H(0x48, Component.Identifier.Key.H),
    
    VK_I(0x49, Component.Identifier.Key.I),
    
    VK_J(0x4A, Component.Identifier.Key.J),
    
    VK_K(0x4B, Component.Identifier.Key.K),
    
    VK_L(0x4C, Component.Identifier.Key.L),
    
    VK_M(0x4D, Component.Identifier.Key.M),
    
    VK_N(0x4E, Component.Identifier.Key.N),
    
    VK_O(0x4F, Component.Identifier.Key.O),
    
    VK_P(0x50, Component.Identifier.Key.P),
    
    VK_Q(0x51, Component.Identifier.Key.Q),
    
    VK_R(0x52, Component.Identifier.Key.R),
    
    VK_S(0x53, Component.Identifier.Key.S),
    
    VK_T(0x54, Component.Identifier.Key.T),
    
    VK_U(0x55, Component.Identifier.Key.U),
    
    VK_V(0x56, Component.Identifier.Key.V),
    
    VK_W(0x57, Component.Identifier.Key.W),
    
    VK_X(0x58, Component.Identifier.Key.X),
    
    VK_Y(0x59, Component.Identifier.Key.Y),
    
    VK_Z(0x5A, Component.Identifier.Key.Z),
    VK_OPEN_BRACKET(0x5B, Component.Identifier.Key.LBRACKET),
    VK_BACK_SLASH(0x5C, Component.Identifier.Key.BACKSLASH),
    VK_CLOSE_BRACKET(0x5D, Component.Identifier.Key.RBRACKET),
    
    VK_NUMPAD0(0x60, Component.Identifier.Key.NUMPAD0),
    
    VK_NUMPAD1(0x61, Component.Identifier.Key.NUMPAD1),
    
    VK_NUMPAD2(0x62, Component.Identifier.Key.NUMPAD2),
    
    VK_NUMPAD3(0x63, Component.Identifier.Key.NUMPAD3),
    
    VK_NUMPAD4(0x64, Component.Identifier.Key.NUMPAD4),
    
    VK_NUMPAD5(0x65, Component.Identifier.Key.NUMPAD5),
    
    VK_NUMPAD6(0x66, Component.Identifier.Key.NUMPAD6),
    
    VK_NUMPAD7(0x67, Component.Identifier.Key.NUMPAD7),
    
    VK_NUMPAD8(0x68, Component.Identifier.Key.NUMPAD8),
    
    VK_NUMPAD9(0x69, Component.Identifier.Key.NUMPAD9),
    
    VK_MULTIPLY(0x6A, Component.Identifier.Key.MULTIPLY),
    
    VK_ADD(0x6B, Component.Identifier.Key.ADD),
    VK_SEPARATER(0x6C, Component.Identifier.Key.UNDERLINE),
    
    VK_SUBTRACT(0x6D, Component.Identifier.Key.SUBTRACT),
    
    VK_DECIMAL(0x6E, Component.Identifier.Key.DECIMAL),
    
    VK_DIVIDE(0x6F, Component.Identifier.Key.DIVIDE),
    
    VK_DELETE(0x7F, Component.Identifier.Key.DELETE), /* ASCII DEL */
    
    VK_NUM_LOCK(0x90, Component.Identifier.Key.NUMLOCK),
    
    VK_SCROLL_LOCK(0x91, Component.Identifier.Key.SCROLL),
    
    VK_F1(0x70, Component.Identifier.Key.F1),
    
    VK_F2(0x71, Component.Identifier.Key.F2),
    
    VK_F3(0x72, Component.Identifier.Key.F3),
    
    VK_F4(0x73, Component.Identifier.Key.F4),
    
    VK_F5(0x74, Component.Identifier.Key.F5),
    
    VK_F6(0x75, Component.Identifier.Key.F6),
    
    VK_F7(0x76, Component.Identifier.Key.F7),
    
    VK_F8(0x77, Component.Identifier.Key.F8),
    
    VK_F9(0x78, Component.Identifier.Key.F9),
    
    VK_F10(0x79, Component.Identifier.Key.F10),
    
    VK_F11(0x7A, Component.Identifier.Key.F11),
    
    VK_F12(0x7B, Component.Identifier.Key.F12),
    VK_F13(0xF000, Component.Identifier.Key.F13),
    VK_F14(0xF001, Component.Identifier.Key.F14),
    VK_F15(0xF002, Component.Identifier.Key.F15),
    VK_F16(0xF003),
    VK_F17(0xF004),
    VK_F18(0xF005),
    VK_F19(0xF006),
    VK_F20(0xF007),
    VK_F21(0xF008),
    VK_F22(0xF009),
    VK_F23(0xF00A),
    /**
     * Constant for the F24 function key.
     * @since 1.2
     */
    VK_F24(0xF00B),
    
    VK_PRINTSCREEN(0x9A),
    
    VK_INSERT(0x9B, Component.Identifier.Key.INSERT),
    
    VK_HELP(0x9C),
    
    VK_META(0x9D),
    
    VK_BACK_QUOTE(0xC0, Component.Identifier.Key.APOSTROPHE),
    
    VK_QUOTE(0xDE),
    VK_KP_UP(0xE0, Component.Identifier.Key.UP),
    VK_KP_DOWN(0xE1, Component.Identifier.Key.DOWN),
    VK_KP_LEFT(0xE2, Component.Identifier.Key.LEFT),
    VK_KP_RIGHT(0xE3, Component.Identifier.Key.RIGHT),
    /* For European keyboards */

    VK_DEAD_GRAVE(0x80),
    
    VK_DEAD_ACUTE(0x81),
    
    VK_DEAD_CIRCUMFLEX(0x82),
    
    VK_DEAD_TILDE(0x83),
    
    VK_DEAD_MACRON(0x84),
    
    VK_DEAD_BREVE(0x85),
    
    VK_DEAD_ABOVEDOT(0x86),
    
    VK_DEAD_DIAERESIS(0x87),
    
    VK_DEAD_ABOVERING(0x88),
    
    VK_DEAD_DOUBLEACUTE(0x89),
    
    VK_DEAD_CARON(0x8a),
    
    VK_DEAD_CEDILLA(0x8b),
    
    VK_DEAD_OGONEK(0x8c),
    
    VK_DEAD_IOTA(0x8d),
    
    VK_DEAD_VOICED_SOUND(0x8e),
    
    VK_DEAD_SEMIVOICED_SOUND(0x8f),
    
    VK_AMPERSAND(0x96),
    
    VK_ASTERISK(0x97),
    
    VK_QUOTEDBL(0x98),
    
    VK_LESS(0x99),
    
    VK_GREATER(0xa0),
    
    VK_BRACELEFT(0xa1),
    
    VK_BRACERIGHT(0xa2),
    VK_AT(0x0200, Component.Identifier.Key.AT),
    VK_COLON(0x0201, Component.Identifier.Key.COLON),
    VK_CIRCUMFLEX(0x0202, Component.Identifier.Key.CIRCUMFLEX),
    VK_DOLLAR(0x0203),
    VK_EURO_SIGN(0x0204),
    VK_EXCLAMATION_MARK(0x0205),
    VK_INVERTED_EXCLAMATION_MARK(0x0206),
    VK_LEFT_PARENTHESIS(0x0207),
    VK_NUMBER_SIGN(0x0208),
    VK_PLUS(0x0209, Component.Identifier.Key.ADD),
    VK_RIGHT_PARENTHESIS(0x020A),
    VK_UNDERSCORE(0x020B, Component.Identifier.Key.UNDERLINE),
    VK_WINDOWS(0x020C, Component.Identifier.Key.LWIN),
    VK_CONTEXT_MENU(0x020D),
    VK_FINAL(0x0018),
    
    /* Japanese PC 106 keyboard, Japanese Solaris keyboard: henkan */
    VK_CONVERT(0x001C, Component.Identifier.Key.CONVERT),
    
    /* Japanese PC 106 keyboard: muhenkan */
    VK_NONCONVERT(0x001D, Component.Identifier.Key.NOCONVERT),
    
    /* Japanese Solaris keyboard: kakutei */
    VK_ACCEPT(0x001E),
    /* not clear what this means - listed in Microsoft Windows API */
    
    VK_MODECHANGE(0x001F),
    /* replaced by VK_KANA_LOCK for Microsoft Windows and Solaris;
       might still be used on other platforms */
    /**
     * Constant for the KANA lock key.
     * @see #VK_KANA_LOCK
     **/
    VK_KANA(0x0015, Component.Identifier.Key.KANA),
    /* replaced by VK_INPUT_METHOD_ON_OFF for Microsoft Windows and Solaris;
       might still be used for other platforms */
    /**
     * Constant for KANJI.
     * @see #VK_INPUT_METHOD_ON_OFF
     */
    VK_KANJI(0x0019, Component.Identifier.Key.KANJI),
    /**
     * Constant for the Alphanumeric function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: eisuu */
    VK_ALPHANUMERIC(0x00F0),
    /**
     * Constant for the Katakana function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: katakana */
    VK_KATAKANA(0x00F1),
    /**
     * Constant for the Hiragana function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: hiragana */
    VK_HIRAGANA(0x00F2),
    /**
     * Constant for the Full-Width Characters function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: zenkaku */
    VK_FULL_WIDTH(0x00F3),
    /**
     * Constant for the Half-Width Characters function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: hankaku */
    VK_HALF_WIDTH(0x00F4),
    /**
     * Constant for the Roman Characters function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: roumaji */
    VK_ROMAN_CHARACTERS(0x00F5),
    /**
     * Constant for the All Candidates function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard - VK_CONVERT + ALT: zenkouho */
    VK_ALL_CANDIDATES(0x0100),
    /**
     * Constant for the Previous Candidate function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard - VK_CONVERT + SHIFT: maekouho */
    VK_PREVIOUS_CANDIDATE(0x0101),
    /**
     * Constant for the Code Input function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard - VK_ALPHANUMERIC + ALT: kanji bangou */
    VK_CODE_INPUT(0x0102),
    /**
     * Constant for the Japanese-Katakana function key.
     * This key switches to a Japanese input method and selects its Katakana input mode.
     * @since 1.2
     */
    /* Japanese Macintosh keyboard - VK_JAPANESE_HIRAGANA + SHIFT */
    VK_JAPANESE_KATAKANA(0x0103),
    /**
     * Constant for the Japanese-Hiragana function key.
     * This key switches to a Japanese input method and selects its Hiragana input mode.
     * @since 1.2
     */
    /* Japanese Macintosh keyboard */
    VK_JAPANESE_HIRAGANA(0x0104),
    /**
     * Constant for the Japanese-Roman function key.
     * This key switches to a Japanese input method and selects its Roman-Direct input mode.
     * @since 1.2
     */
    /* Japanese Macintosh keyboard */
    VK_JAPANESE_ROMAN(0x0105),
    /**
     * Constant for the locking Kana function key.
     * This key locks the keyboard into a Kana layout.
     * @since 1.3
     */
    /* Japanese PC 106 keyboard with special Windows driver - eisuu + Control; Japanese Solaris keyboard: kana */
    VK_KANA_LOCK(0x0106, Component.Identifier.Key.UNKNOWN),
    /**
     * Constant for the input method on/off key.
     * @since 1.3
     */
    /* Japanese PC 106 keyboard: kanji. Japanese Solaris keyboard: nihongo */
    VK_INPUT_METHOD_ON_OFF(0x0107),
    /* for Sun keyboards */
    
    VK_CUT(0xFFD1),
    
    VK_COPY(0xFFCD),
    
    VK_PASTE(0xFFCF),
    
    VK_UNDO(0xFFCB),
    
    VK_AGAIN(0xFFC9),
    
    VK_FIND(0xFFD0),
    
    VK_PROPS(0xFFCA),
    
    VK_STOP(0xFFC8, Component.Identifier.Key.STOP),
    /**
     * Constant for the Compose function key.
     * @since 1.2
     */
    VK_COMPOSE(0xFF20),
    /**
     * Constant for the AltGraph function key.
     * @since 1.2
     */
    VK_ALT_GRAPH(0xFF7E),
    /**
     * Constant for the Begin key.
     * @since 1.5
     */
    VK_BEGIN(0xFF58),
    /**
     * This value is used to indicate that the keyCode is unknown.
     * KEY_TYPED events do not have a keyCode value; this value
     * is used instead.
     */
    VK_UNDEFINED(0x0, Component.Identifier.Key.UNKNOWN),
    /**
     * A constant indicating that the keyLocation is indeterminate
     * or not relevant.
     * {@code KEY_TYPED} events do not have a keyLocation; this value
     * is used instead.
     * @since 1.4
     */
    KEY_LOCATION_UNKNOWN(0),
    /**
     * A constant indicating that the key pressed or released
     * is not distinguished as the left or right version of a key,
     * and did not originate on the numeric keypad (or did not
     * originate with a virtual key corresponding to the numeric
     * keypad).
     * @since 1.4
     */
    KEY_LOCATION_STANDARD(1),
    /**
     * A constant indicating that the key pressed or released is in
     * the left key location (there is more than one possible location
     * for this key).  Example: the left shift key.
     * @since 1.4
     */
    KEY_LOCATION_LEFT(2),
    /**
     * A constant indicating that the key pressed or released is in
     * the right key location (there is more than one possible location
     * for this key).  Example: the right shift key.
     * @since 1.4
     */
    KEY_LOCATION_RIGHT(3),
    /**
     * A constant indicating that the key event originated on the
     * numeric keypad or with a virtual key corresponding to the
     * numeric keypad.
     * @since 1.4
     */
    KEY_LOCATION_NUMPAD(4)
;
    public final int value;
    public final Component.Identifier identifier;
    private VirtualKey(final int value) {
        this(value, Component.Identifier.Key.UNKNOWN);
    }
    private VirtualKey(final int value, final Component.Identifier identifier) {
        this.value = value;
        this.identifier = identifier;
    }

    public static Optional<VirtualKey> parse(final int value) {
        for(VirtualKey key : VirtualKey.values()) {
            if (key.value == value) {
                return Optional.of(key);
            }
        }
        return Optional.empty();
    }

    public static Set<VirtualKey> parse(final Component.Identifier identifier) {
        final Set<VirtualKey> virtualKeys = new HashSet<>();
        for(VirtualKey virtualKey : VirtualKey.values()) {
            if (virtualKey.identifier.equals(identifier)) {
                virtualKeys.add(virtualKey);
            }
        }
        return virtualKeys;
    }

    @Override
    public String toString() {
        final String parentDesc = super.toString();
        final int idx = parentDesc.indexOf('_');
        return parentDesc.substring(idx+1);
    }
}
